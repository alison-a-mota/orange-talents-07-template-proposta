package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.cartao.carteira.paypal.PaypalRepository;
import br.com.zup.proposta.compartilhado.anotacoes.CartaoBloqueado;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static br.com.zup.proposta.cartao.carteira.TipoCarteira.normalizaStatus;

@RestController
@RequestMapping("/api/cartao")
@Validated
public class CarteiraController {

    private final CartaoRepository cartaoRepository;
    private final PaypalRepository paypalRepository;
    private final ClientCartao clientCartao;

    public CarteiraController(CartaoRepository cartaoRepository,
                              PaypalRepository paypalRepository,
                              ClientCartao clientCartao) {
        this.cartaoRepository = cartaoRepository;
        this.paypalRepository = paypalRepository;
        this.clientCartao = clientCartao;
    }

    @PostMapping("/{cartaoId}/carteira")
    public void associa(@Valid @RequestBody CarteiraRequest request,
                        @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId) {

        var cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        var tipoCarteira = normalizaStatus(request.getCarteira());

        if (tipoCarteira.equals(TipoCarteira.PAYPAL) && cartaoRepository.existsByIdAndTipoCarteira(cartaoId, tipoCarteira)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este cartão já está vinculado a uma carteira Paypal");
        }

        var carteiraRequestClient = new CarteiraRequestClient(request.getEmail(), tipoCarteira.toString());

        try {
            var carteiraResponseClient = clientCartao.associaCarteira(cartao.getNumeroCartao(),
                    carteiraRequestClient);

            Assert.isTrue(!carteiraResponseClient.getResultado().isEmpty(), "Houve um problema na API externa");

            var paypal = request.toModel(cartao);
            cartao.atualizaCarteiraCartaoStatus(tipoCarteira);
            paypalRepository.save(paypal);
        } catch (FeignException.InternalServerError | FeignException.UnprocessableEntity ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Houve um problema com a API externa");
        }
    }
}
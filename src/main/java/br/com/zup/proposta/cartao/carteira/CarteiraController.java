package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.cartao.carteira.paypal.PaypalRepository;
import br.com.zup.proposta.cartao.carteira.samsung.SamsungPayRepository;
import br.com.zup.proposta.compartilhado.anotacoes.CartaoBloqueado;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cartao")
@Validated
public class CarteiraController {

    private final CartaoRepository cartaoRepository;
    private final PaypalRepository paypalRepository;
    private final SamsungPayRepository samsungPayRepository;
    private final ClientCartao clientCartao;

    public CarteiraController(CartaoRepository cartaoRepository,
                              PaypalRepository paypalRepository,
                              SamsungPayRepository samsungPayRepository, ClientCartao clientCartao) {
        this.cartaoRepository = cartaoRepository;
        this.paypalRepository = paypalRepository;
        this.samsungPayRepository = samsungPayRepository;
        this.clientCartao = clientCartao;
    }

    @PostMapping("/{cartaoId}/carteira/paypal")
    public void associaPaypal(@Valid @RequestBody CarteiraRequest request,
                              @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId) {

        if(paypalRepository.existsByCartaoId(cartaoId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este cartão já está vinculado a essa carteira");

        var cartao = processa(cartaoId, request);
        var paypal = request.toModelPaypal(cartao);
        paypalRepository.save(paypal);

    }

    @PostMapping("/{cartaoId}/carteira/samsung-pay")
    public void associaSamsungPay(@Valid @RequestBody CarteiraRequest request,
                                  @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId) {

        if(samsungPayRepository.existsByCartaoId(cartaoId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este cartão já está vinculado a essa carteira");

        var cartao = processa(cartaoId, request);
        var samsungPay = request.toModelSamsung(cartao);
        samsungPayRepository.save(samsungPay);
    }

    private Cartao processa(Long cartaoId, CarteiraRequest request) {

        var cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        var carteiraRequestClient = new CarteiraRequestClient(request.getEmail(), request.getCarteira().toString());

        try {
            var carteiraResponseClient = clientCartao.associaCarteira(cartao.getNumeroCartao(),
                    carteiraRequestClient);
            Assert.isTrue(!carteiraResponseClient.getResultado().isEmpty(), "Houve um problema na API externa");

        } catch (FeignException.InternalServerError | FeignException.UnprocessableEntity ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Houve um problema com a API externa");
        }

        return cartao;
    }
}
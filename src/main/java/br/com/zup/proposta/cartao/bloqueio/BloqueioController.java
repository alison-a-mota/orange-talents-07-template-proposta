package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.cartao.CartaoStatus;
import br.com.zup.proposta.compartilhado.anotacoes.CartaoBloqueado;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cartao")
@Validated
public class BloqueioController {

    private final CartaoRepository cartaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final ClientCartao clientCartao;

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository, ClientCartao clientCartao) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.clientCartao = clientCartao;
    }

    @PostMapping("/{cartaoId}/bloqueio")
    public ResponseEntity<?> bloqueia(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("X-Forwarded-For") String ipUsuario,
                                      @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId) {

        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cartão não foi encontrado"));

        try {
            BloqueioCartaoResponseClient responseClient = clientCartao
                    .bloqueia(cartao.getNumeroCartao(), new BloqueioCartaoRequestClient());

            Bloqueio bloqueio = new BloqueioRequest().toModel(ipUsuario, userAgent, cartao);
            cartao.atualizaStatus(responseClient.getResultado());

            bloqueioRepository.save(bloqueio);
            cartaoRepository.save(cartao);

            return ResponseEntity.ok().build();

        } catch (FeignException.UnprocessableEntity ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (FeignException.InternalServerError ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Este cartão já está bloqueado");
        }
    }
}
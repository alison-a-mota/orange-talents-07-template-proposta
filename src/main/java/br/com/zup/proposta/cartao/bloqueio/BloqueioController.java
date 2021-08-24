package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.compartilhado.anotacoes.CartaoBloqueado;
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

    public BloqueioController(CartaoRepository cartaoRepository, BloqueioRepository bloqueioRepository) {
        this.cartaoRepository = cartaoRepository;
        this.bloqueioRepository = bloqueioRepository;
    }

    @PostMapping("/{cartaoId}/bloqueio")
    public ResponseEntity<?> bloqueia(@RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("X-Forwarded-For") String ipUsuario,
                                      @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId) {

        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cartão não foi encontrado"));

        Bloqueio bloqueio = new BloqueioRequest().toModel(ipUsuario, userAgent, cartao);
        bloqueioRepository.save(bloqueio);

        return ResponseEntity.ok().build();
    }
}
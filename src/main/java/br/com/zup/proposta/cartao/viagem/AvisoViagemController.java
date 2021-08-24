package br.com.zup.proposta.cartao.viagem;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import br.com.zup.proposta.compartilhado.anotacoes.CartaoBloqueado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cartao")
@Validated
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;
    private final AvisoViagemRepository avisoViagemRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
    }

    @PostMapping("/{cartaoId}/aviso")
    public ResponseEntity<?> avisaViagem(@RequestBody @Valid AvisoViagemRequest avisoViagemRequest,
                                      @RequestHeader("User-Agent") String userAgent,
                                      @RequestHeader("X-Forwarded-For") String ipUsuario,
                                      @CartaoBloqueado(fieldName = "id", domainClass = Cartao.class) @PathVariable Long cartaoId){

        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O cartão não foi encontrado"));

        //Verifica se existe um aviso de viagem ativo para esse cartão
        if(avisoViagemRepository.existsByCartaoIdAndAtivo(cartaoId, true)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Já existe um aviso de viagem ativo para esse cartão");
        }

        AvisoViagem avisoViagem = avisoViagemRequest.toModel(userAgent, ipUsuario, cartao);
        avisoViagemRepository.save(avisoViagem);

        return ResponseEntity.ok().build();
    }
}

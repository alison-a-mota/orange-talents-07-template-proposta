package br.com.zup.proposta.cartao.biometria;

import br.com.zup.proposta.cartao.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/cartao")
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    /**
     * Cria uma Biometria para um Cartão.
     *
     * @param biometriaRequest É obrigatório passar uma string com o fingerprint e precisa ser Base64.
     * @param cartaoId         É obrigatório.
     */
    @PostMapping("/{cartaoId}/biometria")
    public ResponseEntity<URI> cria(@Valid @RequestBody BiometriaRequest biometriaRequest,
                                    @PathVariable Long cartaoId,
                                    UriComponentsBuilder uriComponentsBuilder) {

        //Verifica se essa biometria já foi cadastrada para esse cartão
        if(biometriaRepository.existsByCartaoIdAndFingerprint(cartaoId, biometriaRequest.getFingerprint())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Esta biometria já está cadastrada para este cartão");
        }

        //Valida se o cartão existe, caso exista, armazena o objeto
        var cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado cartão com esse Id."));

        var biometria = biometriaRequest.toModel(cartao);
        biometriaRepository.save(biometria);

        var uri = uriComponentsBuilder
                .path("/api/cartao/biometria/" + biometria.getId())
                .buildAndExpand().toUri();

        return ResponseEntity.created(uri).build();
    }
}
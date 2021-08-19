package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping
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
    @PostMapping("/api/cartao/{cartaoId}/biometria")
    public ResponseEntity<URI> cria(@Valid @RequestBody BiometriaRequest biometriaRequest, @PathVariable Long cartaoId) {

        //Valida se o cartão existe, caso exista, armazena o objeto
        Cartao cartao = cartaoRepository.findById(cartaoId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado cartão com esse Id."));

        Biometria biometria = biometriaRequest.toModel(cartao);
        biometriaRepository.save(biometria);

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.linktemporario/api")
                .path("biometria/" + biometria.getId())
                .buildAndExpand().toUri();

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri).build();
    }
}
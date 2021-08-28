package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.HashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final HashCode hashCode;

    public PropostaController(PropostaRepository propostaRepository, HashCode hashCode) {
        this.propostaRepository = propostaRepository;
        this.hashCode = hashCode;
    }

    @PostMapping
    public ResponseEntity<URI> cria(@RequestBody @Valid PropostaRequest propostaRequest,
                                    UriComponentsBuilder uriComponentsBuilder) throws NoSuchAlgorithmException {

        //Valida se já existe uma proposta para esse documento e retorna 422 se existir.
        if (propostaRepository.existsByHashDocumento(hashCode.gerarHash(propostaRequest.getDocumento()))) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Encontramos uma proposta para esse documento.");
        }

        Proposta proposta = propostaRequest.toModel(hashCode);
        propostaRepository.save(proposta);

        var uri = uriComponentsBuilder
                .path("/api/proposta/" + proposta.getId())
                .buildAndExpand().toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{propostaId}")
    public ResponseEntity<PropostaResponse> detalha(@PathVariable Long propostaId) {
        var proposta = propostaRepository.findById(propostaId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Não encontramos uma proposta com esse Id."));

        return ResponseEntity.status(HttpStatus.OK).body(new PropostaResponse(proposta));
    }
}
package br.com.zup.proposta.proposta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/proposta")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<URI> cria(@RequestBody @Valid PropostaRequest propostaRequest,
                                    UriComponentsBuilder uriComponentsBuilder) {

        //Valida se já existe uma proposta para esse documento e retorna 422 se existir.
        if (propostaRepository.existsByDocumento(propostaRequest.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Encontramos uma proposta para esse documento.");
        }

        Proposta proposta = propostaRequest.toModel();
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
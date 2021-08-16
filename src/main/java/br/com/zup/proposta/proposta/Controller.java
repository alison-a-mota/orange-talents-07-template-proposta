package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.urls.MontaUri;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/proposta")
public class Controller {

    private final PropostaRepository propostaRepository;
    private final MontaUri montaUri;

    public Controller(PropostaRepository propostaRepository, MontaUri montaUri) {
        this.propostaRepository = propostaRepository;
        this.montaUri = montaUri;
    }

    @PostMapping
    public ResponseEntity<URI> cria(@RequestBody @Valid PropostaRequest propostaRequest) {

        if (propostaRepository.existsByDocumento(propostaRequest.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Encontramos uma proposta para esse endereço.");
        }

        Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);

        URI uri = montaUri.created("proposta", proposta.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri).build();
    }
}

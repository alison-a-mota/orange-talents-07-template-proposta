package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.urls.MontaUri;
import br.com.zup.proposta.proposta.analise.ClientAnalise;
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
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final MontaUri montaUri;
    private final ClientAnalise clientAnalise;

    public PropostaController(PropostaRepository propostaRepository, MontaUri montaUri, ClientAnalise clientAnalise) {
        this.propostaRepository = propostaRepository;
        this.montaUri = montaUri;
        this.clientAnalise = clientAnalise;
    }

    @PostMapping
    public ResponseEntity<URI> cria(@RequestBody @Valid PropostaRequest propostaRequest) {

        if (propostaRepository.existsByDocumento(propostaRequest.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Encontramos uma proposta para esse documento.");
        }

        Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);

        proposta.analisaEAtualizaStatus(clientAnalise);
        propostaRepository.save(proposta);

        URI uri = montaUri.created("proposta", proposta.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri).build();
    }
}

package br.com.zup.proposta.proposta;

import br.com.zup.proposta.clients.ClientAnalise;
import br.com.zup.proposta.proposta.analise.AnaliseRequest;
import br.com.zup.proposta.proposta.analise.AnaliseResponseClient;
import feign.FeignException;
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
    private final ClientAnalise clientAnalise;

    public PropostaController(PropostaRepository propostaRepository, ClientAnalise clientAnalise) {
        this.propostaRepository = propostaRepository;
        this.clientAnalise = clientAnalise;
    }

    @PostMapping
    public ResponseEntity<URI> cria(@RequestBody @Valid PropostaRequest propostaRequest) {

        //Valida se já existe uma proposta para esse documento e retorna 422 se existir.
        if (propostaRepository.existsByDocumento(propostaRequest.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Encontramos uma proposta para esse documento.");
        }

        Proposta proposta = propostaRequest.toModel();
        propostaRepository.save(proposta);

        proposta.analisaEAtualizaStatus(clientAnalise);
        propostaRepository.save(proposta);

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("www.linktemporario/api")
                .path("proposta/" +proposta.getId())
                .buildAndExpand().toUri();

        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri).build();
    }

    @GetMapping("/{propostaId}")
    public ResponseEntity<PropostaResponse> detalha(@PathVariable Long propostaId){
        Proposta proposta = propostaRepository.findById(propostaId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Não encontramos uma proposta com esse Id."));

        return ResponseEntity.status(HttpStatus.OK).body(new PropostaResponse(proposta));
    }


}

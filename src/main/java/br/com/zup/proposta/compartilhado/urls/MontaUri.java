package br.com.zup.proposta.compartilhado.urls;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class MontaUri {

    /**
     * Recebe o endereço do Recurso e o Id e devolve uma URI
     * @param recurso: Inserir o subdomínio sem o "/"
     * @param id: Id do recurso que foi criado
     */
    public URI montaUriResponseCreated(String recurso, Long id) {
        String host = "www.linktemporario/api";
        String path = recurso+ "/" +id;
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .path(path)
                .buildAndExpand().toUri();
    }
}

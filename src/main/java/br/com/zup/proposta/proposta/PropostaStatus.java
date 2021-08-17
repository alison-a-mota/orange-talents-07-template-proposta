package br.com.zup.proposta.proposta;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum PropostaStatus {

    NAO_ELEGIVEL,
    ELEGIVEL;

    /**
     * Devolve o status da proposta conforme o retorno da análise.
     */
    public static PropostaStatus normalizaStatus(String s) {
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A análise não retornou uma resposta válida");
        }

        if (s.equals("SEM_RESTRICAO")) {
            return PropostaStatus.ELEGIVEL;
        }
        if (s.equals("COM_RESTRICAO")) {
            return PropostaStatus.NAO_ELEGIVEL;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deu pau");
    }
}
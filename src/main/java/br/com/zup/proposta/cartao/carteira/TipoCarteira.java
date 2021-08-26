package br.com.zup.proposta.cartao.carteira;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public enum TipoCarteira {
    PAYPAL;

    /**
     * Devolve o status da proposta correto.
     */
    public static TipoCarteira normalizaStatus(String s) {

        if ("PAYPAL".equals(s)) {
            return TipoCarteira.PAYPAL;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carteira Status inválida");
    }
}

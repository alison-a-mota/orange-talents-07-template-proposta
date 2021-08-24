package br.com.zup.proposta.cartao.viagem;

import java.time.LocalDate;

public class AvisoViagemRequestClient {

    private String destino;
    private LocalDate validoAte;

    public AvisoViagemRequestClient(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}

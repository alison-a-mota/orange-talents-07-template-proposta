package br.com.zup.proposta.cartao.viagem;

import br.com.zup.proposta.cartao.Cartao;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @FutureOrPresent
    private LocalDate validoAte;

    public AvisoViagem toModel(String userAgent, String ipCliente,  Cartao cartao) {
        return new AvisoViagem(destino, validoAte, userAgent, ipCliente,  cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}

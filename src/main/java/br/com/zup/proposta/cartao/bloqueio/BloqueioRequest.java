package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BloqueioRequest {

    @NotBlank
    private String ipUsuario;
    @NotBlank
    private String userAgent;
    @NotNull
    private Cartao cartao;

    public Bloqueio toModel(String ipUsuario, String userAgent, Cartao cartao) {
        return new Bloqueio(ipUsuario, userAgent, cartao);
    }
}

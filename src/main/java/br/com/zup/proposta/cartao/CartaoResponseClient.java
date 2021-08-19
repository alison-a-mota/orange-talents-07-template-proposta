package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;

import javax.validation.constraints.NotBlank;

public class CartaoResponseClient {

    @NotBlank
    private String id;
    @NotBlank
    private String emitidoEm;
    @NotBlank
    private String titular;
    private String limite;
    private String renegociacao;

    public Cartao toModel(Proposta proposta) {
        return new Cartao(id, proposta);
    }

    public String getId() {
        return id;
    }

    public String getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public String getLimite() {
        return limite;
    }

    public String getRenegociacao() {
        return renegociacao;
    }
}
package br.com.zup.proposta.proposta.analise;

import br.com.zup.proposta.proposta.Proposta;

public class AnaliseRequest {

    private String idProposta;
    private String nome;
    private String documento;

    public AnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "AnaliseRequest{" +
                "idProposta='" + idProposta + '\'' +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                '}';
    }
}
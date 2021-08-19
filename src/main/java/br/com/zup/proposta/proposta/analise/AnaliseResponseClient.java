package br.com.zup.proposta.proposta.analise;

import br.com.zup.proposta.proposta.PropostaStatus;

public class AnaliseResponseClient {

    private final String idProposta;
    private final String nome;
    private final String documento;
    private final String resultadoSolicitacao;

    public AnaliseResponseClient(String idProposta, String nome, String documento, String resultadoSolicitacao) {
        this.idProposta = idProposta;
        this.nome = nome;
        this.documento = documento;
        this.resultadoSolicitacao = resultadoSolicitacao;
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

    public PropostaStatus getStatus() {
        return PropostaStatus.normalizaStatus(this.resultadoSolicitacao);
    }

    public String getResultadoSolicitacao(){
        return resultadoSolicitacao;
    }

    @Override
    public String toString() {
        return "AnaliseResponse{" +
                "idProposta='" + idProposta + '\'' +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", resultadoSolicitacao='" + resultadoSolicitacao + '\'' +
                '}';
    }
}
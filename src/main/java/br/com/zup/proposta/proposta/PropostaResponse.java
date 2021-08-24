package br.com.zup.proposta.proposta;

public class PropostaResponse {

    private Long idProposta;
    private String nome;
    private PropostaStatus propostaStatus;

    public PropostaResponse(Proposta proposta) {
        this.idProposta = proposta.getId();
        this.nome = proposta.getNome();
        this.propostaStatus = proposta.getPropostaStatus();
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}

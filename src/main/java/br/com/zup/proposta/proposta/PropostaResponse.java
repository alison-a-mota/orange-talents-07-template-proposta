package br.com.zup.proposta.proposta;

public class PropostaResponse {

    private Long propostaId;
    private String nome;
    private PropostaStatus propostaStatus;

    public PropostaResponse(Proposta proposta) {
        this.propostaId = proposta.getId();
        this.nome = proposta.getNome();
        this.propostaStatus = proposta.getPropostaStatus();
    }

    public Long getPropostaId() {
        return propostaId;
    }

    public String getNome() {
        return nome;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}

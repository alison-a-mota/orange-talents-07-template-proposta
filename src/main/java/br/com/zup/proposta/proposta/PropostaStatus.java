package br.com.zup.proposta.proposta;

public enum PropostaStatus {

    EM_ANALISE,
    CARTAO_EMITIDO,
    NAO_ELEGIVEL,
    ELEGIVEL;

    /**
     * Devolve o status da proposta correto.
     */
    public static PropostaStatus normalizaStatus(String s) {

        if (s.equals("SEM_RESTRICAO") || s.equals("ELEGIVEL")) {
            return PropostaStatus.ELEGIVEL;
        }
        if (s.equals("COM_RESTRICAO")) {
            return PropostaStatus.NAO_ELEGIVEL;
        }
        return PropostaStatus.EM_ANALISE;
    }
}
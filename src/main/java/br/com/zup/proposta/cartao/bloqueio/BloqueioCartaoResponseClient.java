package br.com.zup.proposta.cartao.bloqueio;

public class BloqueioCartaoResponseClient {

    private String resultado;

    public BloqueioCartaoResponseClient(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    @Deprecated
    public BloqueioCartaoResponseClient() {
    }
}

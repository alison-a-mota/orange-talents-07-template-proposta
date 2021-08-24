package br.com.zup.proposta.cartao.viagem;

public class AvisoViagemResponseClient {

    private String resultado;

    public AvisoViagemResponseClient(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    @Deprecated
    public AvisoViagemResponseClient() {
    }
}

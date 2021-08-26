package br.com.zup.proposta.cartao.carteira;

public class CarteiraResponseClient {

    private String resultado;
    private String id;

    public CarteiraResponseClient(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }
}

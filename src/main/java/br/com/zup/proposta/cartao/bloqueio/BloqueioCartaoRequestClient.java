package br.com.zup.proposta.cartao.bloqueio;

public class BloqueioCartaoRequestClient {

    private final String sistemaResponsavel;

    public BloqueioCartaoRequestClient() {
        this.sistemaResponsavel = "{$api.cartoes.bloqueio.sistemaresponsavel}";
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}

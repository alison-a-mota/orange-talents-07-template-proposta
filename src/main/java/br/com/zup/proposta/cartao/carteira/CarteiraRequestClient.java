package br.com.zup.proposta.cartao.carteira;

import javax.validation.constraints.Email;

public class CarteiraRequestClient {

    @Email
    private String email;
    private String carteira;

    public CarteiraRequestClient(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
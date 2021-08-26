package br.com.zup.proposta.cartao.carteira;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.carteira.paypal.Paypal;
import br.com.zup.proposta.cartao.carteira.samsung.SamsungPay;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;
    @NotNull
    private TipoCarteira carteira;

    public SamsungPay toModelSamsung(Cartao cartao) {
        return new SamsungPay(email, cartao);
    }

    public Paypal toModelPaypal(Cartao cartao) {
        return new Paypal(email, cartao);
    }

    public CarteiraRequest(@NotBlank String email, @NotNull TipoCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getCarteira() {
        return carteira;
    }
}

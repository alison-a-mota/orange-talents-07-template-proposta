package br.com.zup.proposta.cartao.carteira.paypal;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Paypal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Email
    private String email;
    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull
    private Cartao cartao;

    public Paypal(String email, Cartao cartao) {
        this.email = email;
        this.cartao = cartao;
    }
}

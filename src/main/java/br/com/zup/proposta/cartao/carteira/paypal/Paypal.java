package br.com.zup.proposta.cartao.carteira.paypal;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class Paypal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Email
    private String email;
    @OneToOne
    private Cartao cartao;
}

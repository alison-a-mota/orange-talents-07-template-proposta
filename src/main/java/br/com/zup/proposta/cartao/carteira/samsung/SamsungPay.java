package br.com.zup.proposta.cartao.carteira.samsung;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.cartao.carteira.Carteira;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class SamsungPay implements Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    @Email
    private String email;
    @OneToOne
    @NotNull
    private Cartao cartao;

    public SamsungPay(@NotBlank @Email String email, @NotNull Cartao cartao) {
        this.email = email;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public SamsungPay() {
    }
}

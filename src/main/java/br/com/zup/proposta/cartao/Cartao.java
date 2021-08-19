package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String numeroCartao;
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    public Cartao(String numeroCartao, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.proposta = proposta;
    }

    @Deprecated
    public Cartao() {
    }
}
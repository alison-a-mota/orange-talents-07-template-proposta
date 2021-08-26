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
    @Enumerated(EnumType.STRING)
    private CartaoStatus cartaoStatus = CartaoStatus.ATIVO;


    public Cartao(String numeroCartao, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.proposta = proposta;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public CartaoStatus getCartaoStatus() {
        return cartaoStatus;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public Cartao() {
    }

    public void atualizaParaBloqueado() {
        this.cartaoStatus = CartaoStatus.BLOQUEADO;
    }
}
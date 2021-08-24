package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.bloqueio.Bloqueio;
import br.com.zup.proposta.proposta.Proposta;
import org.springframework.util.Assert;

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

    @Deprecated
    public Cartao() {
    }

    public void atualizaStatus(String resultado) {
        Assert.isTrue(resultado.equals("BLOQUEADO"), "Aconteceu um problema com o sistema de bloqueio");
        this.cartaoStatus = CartaoStatus.BLOQUEADO;
    }
}
package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.carteira.TipoCarteira;
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
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;


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
        Assert.isTrue(this.cartaoStatus.equals(CartaoStatus.BLOQUEADO),
                "Este cartão já está bloqueado");
        this.cartaoStatus = CartaoStatus.BLOQUEADO;
    }

    public void atualizaCarteiraCartaoStatus(TipoCarteira tipoCarteira) {
        Assert.isTrue(this.tipoCarteira == null && TipoCarteira.PAYPAL.equals(tipoCarteira),
                "Este cartão já está associado à uma carteira");
        this.tipoCarteira = tipoCarteira;
    }
}
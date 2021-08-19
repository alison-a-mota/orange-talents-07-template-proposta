package br.com.zup.proposta.cartao;

import br.com.zup.proposta.proposta.Proposta;

import javax.persistence.*;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    @OneToOne
    private Proposta proposta;

    public Cartao(String numeroCartao, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.proposta = proposta;
    }

    @Deprecated
    public Cartao() {
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "id=" + id +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", proposta=" + proposta +
                '}';
    }
}
package br.com.zup.proposta.proposta;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @CpfOuCnpj
    @Column(unique = true)
    private String documento;
    @NotNull
    @Positive
    private BigDecimal salario;
    @Embedded
    @NotNull
    private Endereco endereco;

    @Enumerated(value = EnumType.STRING)
    private PropostaStatus propostaStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    public Proposta(String nome, String email, String documento, BigDecimal salario, Endereco endereco, String status) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.salario = salario;
        this.endereco = endereco;
        this.propostaStatus = PropostaStatus.normalizaStatus(status);
    }

    public void setCartaoEAtualizaStatus(Cartao cartao) {

        Assert.isTrue(PropostaStatus.ELEGIVEL.equals(propostaStatus),
                "Essa proposta não está elegível para gerar um cartão.");

        this.cartao = cartao;
        this.propostaStatus = PropostaStatus.CARTAO_EMITIDO;
    }

    public void atualizaStatus(String status) {
        this.propostaStatus = PropostaStatus.normalizaStatus(status);
    }

    @Deprecated
    public Proposta() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}
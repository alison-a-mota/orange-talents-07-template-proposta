package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;

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
    private String documento;
    @NotNull
    @Positive
    private BigDecimal salario;
    @Embedded
    @NotNull
    private Endereco endereco;

    public Proposta(String nome, String email, String documento, BigDecimal salario, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.salario = salario;
        this.endereco = endereco;
    }

    @Deprecated
    public Proposta() {
    }

    public Long getId() {
        return id;
    }
}

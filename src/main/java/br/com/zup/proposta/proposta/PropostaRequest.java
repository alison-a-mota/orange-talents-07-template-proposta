package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

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

    @NotBlank
    private String rua;
    @NotNull
    private Integer numero;
    private String complemento;
    @NotBlank
    private String cidade;
    @NotBlank
    private String estado;
    @NotBlank
    private String cep;

    public Proposta toModel() {
        var endereco = new Endereco(this.rua, this.numero,
                this.complemento, this.cidade, this.estado, this.cep);
        var status = "EM_ANALISE";
        return new Proposta(nome, email, documento, salario, endereco, status);
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getRua() {
        return rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }
}

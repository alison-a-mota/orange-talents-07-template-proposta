package br.com.zup.proposta.proposta;

import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;
import br.com.zup.proposta.proposta.analise.AnaliseRequest;
import br.com.zup.proposta.proposta.analise.AnaliseResponse;
import br.com.zup.proposta.proposta.analise.ClientAnalise;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    @Enumerated(value = EnumType.STRING)
    private PropostaStatus propostaStatus;

    public Proposta(String nome, String email, String documento, BigDecimal salario, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.salario = salario;
        this.endereco = endereco;
    }

    public void analisaEAtualizaStatus(ClientAnalise clientAnalise) {

        try {
            AnaliseResponse analiseResponse = clientAnalise.analisaSolicitacao(
                    new AnaliseRequest(this));
            atualizaStatus(analiseResponse.getStatus());
        } catch (FeignException ex) {
            atualizaStatus(PropostaStatus.NAO_ELEGIVEL);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Deu um erro estranho");
        }
    }

    private void atualizaStatus(PropostaStatus status) {
        propostaStatus = status;
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
}
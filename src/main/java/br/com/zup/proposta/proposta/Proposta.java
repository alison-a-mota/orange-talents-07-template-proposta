package br.com.zup.proposta.proposta;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;
import br.com.zup.proposta.proposta.analise.AnaliseRequest;
import br.com.zup.proposta.proposta.analise.AnaliseResponseClient;
import br.com.zup.proposta.clients.ClientAnalise;
import feign.FeignException;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Cartao cartao;

    public Proposta(String nome, String email, String documento, BigDecimal salario, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.salario = salario;
        this.endereco = endereco;
    }

    public void setCartaoEAtualizaStatus(Cartao cartao){
        this.cartao = cartao;
        this.propostaStatus = PropostaStatus.CARTAO_EMITIDO;
    }

    public void analisaEAtualizaStatus(ClientAnalise clientAnalise) {
        AnaliseRequest request = new AnaliseRequest(this);

        try {
            AnaliseResponseClient analiseResponse = clientAnalise.analisaSolicitacao(request);
            atualizaStatus(analiseResponse.getStatus());
        } catch (FeignException.UnprocessableEntity ex) {
            atualizaStatus(PropostaStatus.NAO_ELEGIVEL);
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

    public Cartao getCartao() {
        return cartao;
    }
}
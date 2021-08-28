package br.com.zup.proposta.proposta;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.HashCode;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.*;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false, unique = true)
    private byte[] hashDocumento;

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
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

    /**
     * @param hashCode Este construtor recebe um documento em formato de String (documento limpo) e converte em HashCode
     *                 para comparações e validações, e criptografa para usar os dados quando necessário.
     */
    public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
                    @NotNull @Positive BigDecimal salario, @NotNull Endereco endereco,
                    String status, HashCode hashCode) throws NoSuchAlgorithmException {
        this.nome = nome;
        this.email = email;
        this.documento = Encryptors.text("abcabc", "cbacba").encrypt(documento);
        this.salario = salario;
        this.endereco = endereco;
        this.propostaStatus = PropostaStatus.normalizaStatus(status);
        this.hashDocumento = hashCode.gerarHash(documento);
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
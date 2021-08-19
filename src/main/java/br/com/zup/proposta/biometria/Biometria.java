package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.anotacoes.IsBase64;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private final LocalDateTime instanteCadastro = LocalDateTime.now();

    @ManyToOne
    @NotNull
    private Cartao cartao;
    @NotBlank
    @IsBase64
    @Column(unique = true)
    private String fingerprint;

    public Biometria(Cartao cartao, String fingerprint) {
        this.cartao = cartao;
        this.fingerprint = fingerprint;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public Biometria() {
    }
}

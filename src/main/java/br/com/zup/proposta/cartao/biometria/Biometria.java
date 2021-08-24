package br.com.zup.proposta.cartao.biometria;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$")
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

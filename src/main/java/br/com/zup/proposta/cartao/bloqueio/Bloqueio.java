package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime instanteBloqueio;
    @NotBlank
    private String ipUsuario;
    @NotBlank
    private String userAgent;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    public Bloqueio(String clienteIp, String userAgent, Cartao cartao) {
        this.instanteBloqueio = LocalDateTime.now();
        this.ipUsuario = clienteIp;
        this.userAgent = userAgent;
        this.cartao = cartao;

        cartao.atualizaParaBloqueado();
    }

    public Cartao getCartao() {
        return cartao;
    }

    @Deprecated
    public Bloqueio() {
    }
}

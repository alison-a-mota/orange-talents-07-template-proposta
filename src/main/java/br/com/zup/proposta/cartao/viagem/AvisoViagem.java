package br.com.zup.proposta.cartao.viagem;

import br.com.zup.proposta.cartao.Cartao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Column(updatable = false)
    private final LocalDateTime instanteDoAviso = LocalDateTime.now();

    @NotBlank
    private String destino;
    @NotNull
    @FutureOrPresent
    private LocalDate validoAte;
    private Boolean ativo = true;
    private String ipCliente;
    private String userAgent;
    private Boolean comunicado = false;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    public AvisoViagem(String destino, LocalDate validoAte, String userAgent, String ipCliente, Cartao cartao) {
        this.destino = destino;
        this.validoAte = validoAte;
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    @Deprecated
    public AvisoViagem() {
    }

    public void atualizaComunicado(AvisoViagemResponseClient responseClient) {
        Assert.isTrue(responseClient.getResultado().equals("CRIADO"),
                "A resposta do sistema externo foi inválida.");
        this.comunicado = true;
    }
}

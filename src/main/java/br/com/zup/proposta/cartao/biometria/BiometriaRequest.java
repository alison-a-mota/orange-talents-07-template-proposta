package br.com.zup.proposta.cartao.biometria;

import br.com.zup.proposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BiometriaRequest {

    @NotBlank
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$")
    private String fingerprint;

    public Biometria toModel(Cartao cartao) {
        return new Biometria(cartao, fingerprint);
    }

    public String getFingerprint() {
        return fingerprint;
    }
}

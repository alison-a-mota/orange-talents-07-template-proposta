package br.com.zup.proposta.biometria;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.anotacoes.CampoUnico;
import br.com.zup.proposta.compartilhado.anotacoes.IsBase64;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    @CampoUnico(fieldName = "fingerprint", domainClass = Biometria.class)
    @IsBase64
    private String fingerprint;

    public Biometria toModel(Cartao cartao) {
        return new Biometria(cartao, fingerprint);
    }

    public String getFingerprint() {
        return fingerprint;
    }
}

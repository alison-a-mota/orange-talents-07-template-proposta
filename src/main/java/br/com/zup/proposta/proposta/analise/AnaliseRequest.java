package br.com.zup.proposta.proposta.analise;

import br.com.zup.proposta.compartilhado.anotacoes.CpfOuCnpj;
import br.com.zup.proposta.proposta.Proposta;
import org.springframework.security.crypto.encrypt.Encryptors;

public class AnaliseRequest {

    private final String idProposta;
    private final String nome;
    @CpfOuCnpj
    private final String documento;

    public AnaliseRequest(Proposta proposta) {
        this.documento = Encryptors.text("abcabc", "cbacba").decrypt(proposta.getDocumento());
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }
}
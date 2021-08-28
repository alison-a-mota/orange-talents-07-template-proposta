package br.com.zup.proposta.compartilhado.clients;

import br.com.zup.proposta.proposta.analise.AnaliseRequest;
import br.com.zup.proposta.proposta.analise.AnaliseResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@FeignClient(name = "analise", url = "${api.analise.solicitacao}")
public interface ClientAnalise {

    @PostMapping("/solicitacao")
    AnaliseResponseClient analisaSolicitacao(@Valid AnaliseRequest request);

}

package br.com.zup.proposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analise", url = "${api.analise.solicitacao}")
public interface ClientAnalise {

    @PostMapping("/api/solicitacao")
    AnaliseResponse analisaSolicitacao(AnaliseRequest request);

}

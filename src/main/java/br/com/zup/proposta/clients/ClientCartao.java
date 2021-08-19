package br.com.zup.proposta.clients;

import br.com.zup.proposta.cartao.CartaoResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = "${api.cartoes}")
public interface ClientCartao {

    @GetMapping("/cartoes")
    CartaoResponseClient getCartoes(@RequestParam String idProposta);
}

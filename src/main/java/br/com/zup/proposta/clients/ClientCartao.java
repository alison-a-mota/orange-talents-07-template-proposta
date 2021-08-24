package br.com.zup.proposta.clients;

import br.com.zup.proposta.cartao.CartaoResponseClient;
import br.com.zup.proposta.cartao.bloqueio.BloqueioCartaoResponseClient;
import br.com.zup.proposta.cartao.bloqueio.BloqueioCartaoRequestClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cartao", url = "${api.cartoes}")
public interface ClientCartao {

    @GetMapping
    CartaoResponseClient getCartoes(@RequestParam String idProposta);

    @PostMapping("/{cartaoId}/bloqueios")
    BloqueioCartaoResponseClient bloqueia(@RequestParam String cartaoId, BloqueioCartaoRequestClient request);
}

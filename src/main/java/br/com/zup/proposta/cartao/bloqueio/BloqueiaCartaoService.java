package br.com.zup.proposta.cartao.bloqueio;

import br.com.zup.proposta.cartao.Cartao;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BloqueiaCartaoService {

    private final ClientCartao clientCartao;

    public BloqueiaCartaoService(ClientCartao clientCartao) {
        this.clientCartao = clientCartao;
    }

    public void bloqueiaCartao(Cartao cartao) {

        try {
            var responseClient = clientCartao
                    .bloqueia(cartao.getNumeroCartao(), new BloqueioCartaoRequestClient());
            Assert.isTrue("BLOQUEADO".equals(responseClient.getResultado()),
                    "O sistema externo retornou um problema ao tentar bloquear esse carto.");
        } catch (FeignException.UnprocessableEntity | FeignException.InternalServerError ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}


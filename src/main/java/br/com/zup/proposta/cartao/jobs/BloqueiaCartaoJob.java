package br.com.zup.proposta.cartao.jobs;

import br.com.zup.proposta.cartao.bloqueio.BloqueioCartaoRequestClient;
import br.com.zup.proposta.cartao.bloqueio.BloqueioRepository;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class BloqueiaCartaoJob {

    private final BloqueioRepository bloqueioRepository;
    private final ClientCartao clientCartao;

    public BloqueiaCartaoJob(BloqueioRepository bloqueioRepository, ClientCartao clientCartao) {
        this.bloqueioRepository = bloqueioRepository;
        this.clientCartao = clientCartao;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.bloqueiaCartao}")
    public void bloqueiaCartao() {

        var bloqueios = bloqueioRepository.findFirst100ByComunicadoIsFalse();

        bloqueios.forEach(bloqueio -> {
            try {
                var responseClient = clientCartao
                        .bloqueia(bloqueio.getCartao().getNumeroCartao(), new BloqueioCartaoRequestClient());
                if (responseClient.getResultado().equals("BLOQUEADO")) {
                    bloqueio.atualizaComunicado(responseClient);
                    bloqueioRepository.save(bloqueio);
                }
            } catch (
                    FeignException.UnprocessableEntity | FeignException.InternalServerError ex) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        });
    }
}

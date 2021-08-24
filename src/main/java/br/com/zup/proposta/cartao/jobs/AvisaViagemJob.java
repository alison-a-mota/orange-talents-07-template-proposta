package br.com.zup.proposta.cartao.jobs;

import br.com.zup.proposta.cartao.viagem.AvisoViagem;
import br.com.zup.proposta.cartao.viagem.AvisoViagemRepository;
import br.com.zup.proposta.cartao.viagem.AvisoViagemRequestClient;
import br.com.zup.proposta.cartao.viagem.AvisoViagemResponseClient;
import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvisaViagemJob {

    private final AvisoViagemRepository avisoViagemRepository;
    private final ClientCartao clientCartao;

    public AvisaViagemJob(AvisoViagemRepository avisoViagemRepository, ClientCartao clientCartao) {
        this.avisoViagemRepository = avisoViagemRepository;
        this.clientCartao = clientCartao;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.avisaViagem}")
    public void avisaViagem() {

        List<AvisoViagem> avisos = avisoViagemRepository.findFirst100ByComunicado(false)
                .stream().filter(AvisoViagem::getAtivo)
                .collect(Collectors.toList());

        avisos.forEach(avisoViagem -> {

            try {
                AvisoViagemResponseClient responseClient = clientCartao
                        .avisaViagem(avisoViagem.getCartao().getNumeroCartao(),
                                new AvisoViagemRequestClient(avisoViagem.getDestino(), avisoViagem.getValidoAte()));

                if (responseClient.getResultado().equals("CRIADO")) {
                    avisoViagem.atualizaComunicado(responseClient);
                    avisoViagemRepository.save(avisoViagem);
                }
            } catch (FeignException.InternalServerError ex) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro na aplicação externa");
            }
        });

    }
}

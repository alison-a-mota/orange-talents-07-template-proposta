package br.com.zup.proposta.cartao.jobs;

import br.com.zup.proposta.compartilhado.clients.ClientCartao;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaStatus;
import feign.FeignException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConsultaCartaoEmitidoJob {

    private final PropostaRepository propostaRepository;
    private final ClientCartao clientCartao;

    public ConsultaCartaoEmitidoJob(PropostaRepository propostaRepository,
                                    ClientCartao clientCartao) {
        this.propostaRepository = propostaRepository;
        this.clientCartao = clientCartao;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.consultaCartaoEmitidoClient}")
    public void consultaCartaoEmitidoClient() {

        //Localiza as propostas que estão com status ELEGIVEL e sem cartão emitido
        var propostas = propostaRepository
                .findFirst100ByPropostaStatus(PropostaStatus.ELEGIVEL)
                //Valida que a lista não tenha proposta com cartão emitido
                .parallelStream().filter(proposta -> proposta.getCartao() == null)
                .collect(Collectors.toList());

        propostas.forEach(proposta -> {
            try {
                var response = clientCartao.getCartoes(proposta.getId().toString());
                if (response.getId() != null) {
                    var cartao = response.toModel(proposta);
                    proposta.setCartaoEAtualizaStatus(cartao);
                    propostaRepository.save(proposta);
                }
            } catch (FeignException.InternalServerError ignored) {
            }
        });
    }
}
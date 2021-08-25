package br.com.zup.proposta.cartao.jobs;

import br.com.zup.proposta.compartilhado.clients.ClientAnalise;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaStatus;
import br.com.zup.proposta.proposta.analise.AnaliseRequest;
import feign.FeignException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnalisaProposta {

    private final PropostaRepository propostaRepository;
    private final ClientAnalise clientAnalise;

    public AnalisaProposta(PropostaRepository propostaRepository, ClientAnalise clientAnalise) {
        this.propostaRepository = propostaRepository;
        this.clientAnalise = clientAnalise;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.analisaProposta}")
    public void analisaEAtualizaStatus() {

        var propostas = propostaRepository
                .findFirst100ByPropostaStatusAndCartaoIsNull(PropostaStatus.EM_ANALISE);

        propostas.forEach(proposta -> {

            try {
                var analiseResponse = clientAnalise.analisaSolicitacao(new AnaliseRequest(proposta));
                proposta.atualizaStatus(analiseResponse.getStatus().toString());
                propostaRepository.save(proposta);
            } catch (FeignException.UnprocessableEntity ex) {
                proposta.atualizaStatus("COM_RESTRICAO");
                propostaRepository.save(proposta);
            }
        });
    }
}
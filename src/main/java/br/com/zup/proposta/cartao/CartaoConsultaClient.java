package br.com.zup.proposta.cartao;

import br.com.zup.proposta.clients.ClientCartao;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartaoConsultaClient {

    private final PropostaRepository propostaRepository;
    private final ClientCartao clientCartao;

    public CartaoConsultaClient(PropostaRepository propostaRepository,
                                ClientCartao clientCartao) {
        this.propostaRepository = propostaRepository;
        this.clientCartao = clientCartao;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.getcartoes}")
    private void consultaClientCartao() {

        //Localiza as propostas que estão com status ELEGIVEL e sem cartão emitido
        List<Proposta> propostas = propostaRepository
                .findAllByPropostaStatus(PropostaStatus.ELEGIVEL)
                .stream().filter(proposta -> proposta.getCartao() == null)
                .collect(Collectors.toList());

        propostas.forEach(proposta -> {
            CartaoResponseClient response = clientCartao.getCartoes(proposta.getId().toString());
            if (response.getId() != null) {
                Cartao cartao = response.toModel(proposta);
                proposta.setCartaoEAtualizaStatus(cartao);
                propostaRepository.save(proposta);
            }
        });
    }
}

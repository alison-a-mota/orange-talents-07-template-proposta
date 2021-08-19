package br.com.zup.proposta.cartao;

import br.com.zup.proposta.clients.ClientCartao;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import br.com.zup.proposta.proposta.PropostaStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    private final PropostaRepository propostaRepository;
    private final CartaoRepository cartaoRepository;
    private final ClientCartao clientCartao;

    public CartaoService(PropostaRepository propostaRepository, CartaoRepository cartaoRepository, ClientCartao clientCartao) {
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.clientCartao = clientCartao;
    }

    @Scheduled(fixedDelayString = "${api.cartoes.delay.getcartoes}")
    private void consultaClientCartao() {

        System.out.println("Ping");

        List<Proposta> propostas = propostaRepository
                .findAllByPropostaStatus(PropostaStatus.ELEGIVEL)
                .stream().filter(proposta -> proposta.getCartao() == null)
                .collect(Collectors.toList());

        propostas.forEach(proposta -> {
            CartaoResponseClient response = clientCartao.getCartoes(proposta.getId().toString());
            if (response.getId() != null) {
                Cartao cartao = response.toModel(proposta);
                cartaoRepository.save(cartao);

                System.out.println("foi");
            }
        });
    }
}

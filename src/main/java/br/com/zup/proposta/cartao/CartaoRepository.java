package br.com.zup.proposta.cartao;

import br.com.zup.proposta.cartao.carteira.TipoCarteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    boolean existsByIdAndTipoCarteira(Long cartaoId, TipoCarteira tipoCarteiraAceitas);
}

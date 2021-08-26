package br.com.zup.proposta.cartao.carteira.paypal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaypalRepository extends JpaRepository<Paypal, Long> {
    boolean existsByCartaoId(Long cartaoId);
}

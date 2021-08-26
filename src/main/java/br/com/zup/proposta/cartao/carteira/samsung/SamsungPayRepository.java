package br.com.zup.proposta.cartao.carteira.samsung;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SamsungPayRepository extends JpaRepository<SamsungPay, Long> {
    boolean existsByCartaoId(Long cartaoId);
}

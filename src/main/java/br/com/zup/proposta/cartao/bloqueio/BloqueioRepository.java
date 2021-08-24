package br.com.zup.proposta.cartao.bloqueio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {
    List<Bloqueio> findFirst100ByComunicadoIsFalse();
}

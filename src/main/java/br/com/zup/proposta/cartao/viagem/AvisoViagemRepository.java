package br.com.zup.proposta.cartao.viagem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisoViagemRepository extends JpaRepository<AvisoViagem, Long> {
    boolean existsByCartaoIdAndAtivo(Long cartaoId, boolean b);

    List<AvisoViagem> findFirst100ByComunicado(boolean b);
}

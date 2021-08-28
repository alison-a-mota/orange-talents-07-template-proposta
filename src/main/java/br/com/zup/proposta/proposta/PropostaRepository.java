package br.com.zup.proposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByHashDocumento(String documento);

    List<Proposta> findFirst100ByPropostaStatus(PropostaStatus status);

    List<Proposta> findFirst100ByPropostaStatusAndCartaoIsNull(PropostaStatus status);

    boolean existsByHashDocumento(byte[] gerarHash);
}

package br.ufpr.monge.repository;

import br.ufpr.monge.model.LancamentoSemanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoSemanalRepository extends JpaRepository<LancamentoSemanal, Long> {
    List<LancamentoSemanal> findByBolsistaId(Long bolsistaId);
    List<LancamentoSemanal> findByProjetoId(Long projetoId);
    List<LancamentoSemanal> findByOrientadorAvaliadorId(Long orientadorId);
}

package br.ufpr.monge.repository;

import br.ufpr.monge.model.BolsistaProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BolsistaProjetoRepository extends JpaRepository<BolsistaProjeto, Long> {
    List<BolsistaProjeto> findByBolsistaId(Long bolsistaId);
    List<BolsistaProjeto> findByProjetoId(Long projetoId);
    List<BolsistaProjeto> findByOrientadorId(Long orientadorId);
    boolean existsByOrientadorIdAndProjetoId(Long orientadorId, Long projetoId);
}
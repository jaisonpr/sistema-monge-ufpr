package br.ufpr.monge.repository;

import br.ufpr.monge.model.LancamentoSemanal;
import br.ufpr.monge.model.enums.StatusLancamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LancamentoSemanalRepository extends JpaRepository<LancamentoSemanal, Long> {
    List<LancamentoSemanal> findByStatusLancamento(StatusLancamento status);
    
    @Query("SELECT COUNT(bp) > 0 FROM BolsistaProjeto bp " +
           "WHERE bp.orientador.id = :orientadorId AND bp.projeto.id = :projetoId")
    boolean orientadorTemPermissaoParaProjeto(@Param("orientadorId") Long orientadorId, 
                                            @Param("projetoId") Long projetoId);
    
    List<LancamentoSemanal> findByBolsistaId(Long bolsistaId);
    List<LancamentoSemanal> findByProjetoId(Long projetoId);
    List<LancamentoSemanal> findByOrientadorAvaliadorId(Long orientadorId);
}

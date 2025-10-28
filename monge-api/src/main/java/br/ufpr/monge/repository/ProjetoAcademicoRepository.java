package br.ufpr.monge.repository;

import br.ufpr.monge.model.ProjetoAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjetoAcademicoRepository extends JpaRepository<ProjetoAcademico, Long> {
    Optional<ProjetoAcademico> findByCodigo(String codigo);
}
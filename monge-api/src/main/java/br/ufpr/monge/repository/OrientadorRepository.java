package br.ufpr.monge.repository;

import br.ufpr.monge.model.Orientador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrientadorRepository extends JpaRepository<Orientador, Long> {
    Optional<Orientador> findBySiape(String siape);
}
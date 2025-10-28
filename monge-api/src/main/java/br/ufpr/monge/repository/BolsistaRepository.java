package br.ufpr.monge.repository;

import br.ufpr.monge.model.Bolsista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BolsistaRepository extends JpaRepository<Bolsista, Long> {
    Optional<Bolsista> findByCpf(String cpf);
    Optional<Bolsista> findByMatricula(String matricula);
}
package br.ufpr.monge.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orientador")
@PrimaryKeyJoinColumn(name = "usuario_id") // FK para a tabela USUARIO
@Data
@EqualsAndHashCode(callSuper = true)
public class Orientador extends Usuario {

    @Column(nullable = false, unique = true, length = 10)
    private String siape;

    @Column(length = 100)
    private String departamento;

    @Column(length = 50)
    private String titulacao;

    @Column(name = "telefone_contato", length = 15)
    private String telefoneContato;

    @OneToMany(mappedBy = "orientador", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<BolsistaProjeto> vinculosSupervisionados = new HashSet<>();

    @OneToMany(mappedBy = "orientadorAvaliador", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<LancamentoSemanal> lancamentosAvaliados = new HashSet<>();
}
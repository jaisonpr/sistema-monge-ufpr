package br.ufpr.monge.model;

import br.ufpr.monge.model.enums.StatusVinculo;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "bolsista_projeto")
@Data
public class BolsistaProjeto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bolsista_id", nullable = false)
    private Bolsista bolsista;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private ProjetoAcademico projeto;

    @ManyToOne
    @JoinColumn(name = "orientador_id", nullable = false)
    private Orientador orientador;

    @Column(name = "data_vinculacao", nullable = false)
    private LocalDate dataVinculacao;

    @Column(name = "data_desvinculacao")
    private LocalDate dataDesvinculacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_vinculo", nullable = false, length = 20)
    private StatusVinculo statusVinculo;
}
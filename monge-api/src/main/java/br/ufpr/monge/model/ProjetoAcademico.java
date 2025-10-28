package br.ufpr.monge.model;

import br.ufpr.monge.model.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projeto_academico")
@Data
public class ProjetoAcademico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Lob
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(precision = 12, scale = 2)
    private BigDecimal orcamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_projeto", nullable = false, length = 20)
    private StatusProjeto statusProjeto;

    @Column(name = "unidade_vinculada", nullable = false, length = 100)
    private String unidadeVinculada;

    @Column(name = "programa_fomento", length = 100)
    private String programaFomento;
    
    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<BolsistaProjeto> vinculos = new HashSet<>();

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<LancamentoSemanal> lancamentos = new HashSet<>();
}
package br.ufpr.monge.model;

import br.ufpr.monge.model.enums.StatusLancamento;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamento_semanal")
@Data
public class LancamentoSemanal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bolsista_id", nullable = false)
    private Bolsista bolsista;

    @ManyToOne
    @JoinColumn(name = "projeto_id", nullable = false)
    private ProjetoAcademico projeto;

    @Column(name = "semana_referencia", nullable = false)
    private LocalDate semanaReferencia;

    @CreationTimestamp
    @Column(name = "data_lancamento", nullable = false, updatable = false)
    private LocalDateTime dataLancamento;

    @Lob
    @Column(name = "atividades_realizadas", nullable = false)
    private String atividadesRealizadas;

    @Column(name = "horas_realizadas", nullable = false)
    private Integer horasRealizadas;

    @Lob
    private String observacoes;

    @Lob
    @Column(name = "justificativa_falta")
    private String justificativaFalta;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_lancamento", nullable = false, length = 20)
    private StatusLancamento statusLancamento;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @Lob
    @Column(name = "feedback_orientador")
    private String feedbackOrientador;

    @ManyToOne
    @JoinColumn(name = "orientador_avaliador_id")
    private Orientador orientadorAvaliador;
}
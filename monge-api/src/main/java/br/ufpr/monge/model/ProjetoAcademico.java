package br.ufpr.monge.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projetos_academicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjetoAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String codigo;
    
    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusProjeto status;

    @Column(nullable = false, length = 120)
    private String unidadeAdministrativa; // ex: PROGRAD, PROGRAP etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orientador_id", nullable = false)
    private Orientador orientadorResponsavel;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bolsista> bolsistas;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LancamentoSemanal> lancamentos;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RelatorioConsolidado> relatorios;

    // === Métodos de domínio ===

    public void adicionarBolsista(Bolsista bolsista) {
        bolsista.setProjeto(this);
        this.bolsistas.add(bolsista);
    }

    public void adicionarLancamento(LancamentoSemanal lancamento) {
        lancamento.setProjeto(this);
        this.lancamentos.add(lancamento);
    }

    public void gerarRelatorio(RelatorioConsolidado relatorio) {
        relatorio.setProjeto(this);
        this.relatorios.add(relatorio);
    }

    public boolean estaAtivo() {
        return this.status == StatusProjeto.EM_EXECUCAO;
    }

    public double calcularPercentualConclusao() {
        long total = lancamentos.size();
        long aprovados = lancamentos.stream()
                .filter(l -> l.getStatus() == StatusLancamento.APROVADO)
                .count();
        return total == 0 ? 0.0 : (aprovados / (double) total) * 100.0;
    }
}

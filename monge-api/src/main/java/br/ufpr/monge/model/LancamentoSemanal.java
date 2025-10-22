package br.ufpr.monge.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamentos_semanais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoSemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate semanaReferencia;

    @Column(nullable = false)
    private LocalDateTime dataLancamento;

    @Column(nullable = false)
    private String atividadesRealizadas;

    @Column(nullable = false)
    private Integer horasRealizadas;

    @Column(nullable = false)
    private String observacoes;

    @Column(nullable = false)
    private String justificativaFalta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusLancamento statusLancamento;

    @Column(nullable = false)
    private LocalDateTime dataAprovacao;

    @Column(length = 255)
    private String feedbackOrientador;

    public void submeter() {
        // Será implementado no futuro
    }

    public Boolean editar() {
        // Será implementado no futuro
        return true;
    }

    public void aprovar() {
        // Será implementado no futuro
    }

    public void rejeitarComMotivo(String motivo) {
        // Será implementado no futuro
    }

    public double calcularFrequencia() {
        // Será implementado no futuro
        return 1.0;
    }
}

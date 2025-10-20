package br.ufpr.monge.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bolsistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bolsista extends Usuario {

    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(length = 15)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Integer cargaHorariaSemanal

    @Column(nullable = false)
    private LocalDate dataInicioVinculo

    @Column(nullable = false)
    private LocalDate dataFimVinculo

    @Column(nullable = false)
    private StatusBolsa StatusBolsa

    public LancamentoSemanal registrarLancamento() {

    }

    public List<LancamentoSemanal> consultarHistorico() {

    }

    public Integer calcularHorasRealizadas() {
        
    }
}
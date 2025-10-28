package br.ufpr.monge.model;

import br.ufpr.monge.model.enums.StatusBolsa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bolsista")
@PrimaryKeyJoinColumn(name = "usuario_id") // FK para a tabela USUARIO
@Data
@EqualsAndHashCode(callSuper = true)
public class Bolsista extends Usuario {

    @Column(unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 15)
    private String telefone;

    @Lob
    private String endereco;

    @Column(name = "carga_horaria_semanal", nullable = false)
    private Integer cargaHorariaSemanal;

    @Column(name = "data_inicio_vinculo", nullable = false)
    private LocalDate dataInicioVinculo;

    @Column(name = "data_fim_vinculo")
    private LocalDate dataFimVinculo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_bolsa", nullable = false, length = 20)
    private StatusBolsa statusBolsa;

    @OneToMany(mappedBy = "bolsista", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<BolsistaProjeto> vinculos = new HashSet<>();

    @OneToMany(mappedBy = "bolsista", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<LancamentoSemanal> lancamentos = new HashSet<>();
}
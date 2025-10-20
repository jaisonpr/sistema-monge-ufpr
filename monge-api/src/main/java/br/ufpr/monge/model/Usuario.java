package br.ufpr.monge.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PerfilUsuario perfil;
    
    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataUltimoAcesso;

    public Boolean autenticar() {
        this.ativo = true;
        this.atualizarUltimoAcesso();
        
        return true;
    }

    public void atualizarUltimoAcesso() {
        this.dataUltimoAcesso = LocalDateTime.now();
    }
}

package br.ufpr.monge.model;

import br.ufpr.monge.model.enums.TipoNotificacao;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacao")
@Data
public class Notificacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "lancamento_id") // Pode ser nulo
    private LancamentoSemanal lancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacao", nullable = false, length = 30)
    private TipoNotificacao tipoNotificacao;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Lob
    @Column(nullable = false)
    private String mensagem;

    @CreationTimestamp
    @Column(name = "data_envio", nullable = false, updatable = false)
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private Boolean lida = false;

    @Column(length = 200)
    private String url;
}
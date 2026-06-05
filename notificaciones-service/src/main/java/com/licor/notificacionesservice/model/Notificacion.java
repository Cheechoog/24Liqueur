package com.licor.notificacionesservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notificaciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notificacion {

    public enum Tipo { EMAIL, PUSH, SMS }
    public enum Estado { PENDIENTE, ENVIADA, FALLIDA }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    @Column(nullable = false, columnDefinition = "tipo_notif")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(length = 200)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(nullable = false, columnDefinition = "estado_notif")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    @Column(length = 200)
    private String referencia;

    @Column(nullable = false)
    private Integer intentos = 0;

    @Column(name = "enviada_en")
    private LocalDateTime enviadaEn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

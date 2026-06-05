package com.licor.tiendasservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tiendas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "propietario_id", nullable = false)
    private UUID propietarioId;

    @Column(length = 20, unique = true)
    private String nit;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String email;

    @Column(nullable = false, length = 300)
    private String direccion;

    @Column(nullable = false, length = 100)
    private String ciudad;

    @Column(precision = 10, scale = 8)
    private BigDecimal lat;

    @Column(precision = 11, scale = 8)
    private BigDecimal lng;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio = LocalTime.of(0, 0);

    @Column(name = "horario_fin", nullable = false)
    private LocalTime horarioFin = LocalTime.of(23, 59);

    @Column(name = "radio_envio_km", nullable = false)
    private Integer radioEnvioKm = 5;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

package com.licor.enviosservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pedido_id", nullable = false, unique = true)
    private UUID pedidoId;

    @Column(name = "repartidor_id")
    private UUID repartidorId;

    @Column(nullable = false, columnDefinition = "estado_envio")
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado = EstadoEnvio.ASIGNADO;

    @Column(name = "direccion_origen", nullable = false, columnDefinition = "TEXT")
    private String direccionOrigen;

    @Column(name = "direccion_destino", nullable = false, columnDefinition = "TEXT")
    private String direccionDestino;

    @Column(name = "lat_destino", precision = 10, scale = 8)
    private BigDecimal latDestino;

    @Column(name = "lng_destino", precision = 11, scale = 8)
    private BigDecimal lngDestino;

    @Column(name = "tiempo_estimado")
    private Integer tiempoEstimado;

    @Column(name = "codigo_confirm", length = 10)
    private String codigoConfirm;

    @Column(name = "entregado_en")
    private LocalDateTime entregadoEn;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

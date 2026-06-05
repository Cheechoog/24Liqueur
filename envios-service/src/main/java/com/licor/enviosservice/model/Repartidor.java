package com.licor.enviosservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "repartidores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(length = 50)
    private String vehiculo;

    @Column(length = 20)
    private String placa;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "lat_actual", precision = 10, scale = 8)
    private BigDecimal latActual;

    @Column(name = "lng_actual", precision = 11, scale = 8)
    private BigDecimal lngActual;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

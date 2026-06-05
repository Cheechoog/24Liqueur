package com.licor.pedidosservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "items_pedido")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Column(name = "nombre_prod", nullable = false, length = 200)
    private String nombreProd;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnit;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}

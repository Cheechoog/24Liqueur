package com.licor.pedidosservice.dto;

import com.licor.pedidosservice.model.EstadoPedido;
import com.licor.pedidosservice.model.ItemPedido;
import com.licor.pedidosservice.model.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PedidoDto {

    @Getter @Setter
    public static class ItemRequest {
        @NotNull private UUID productoId;
        @NotBlank private String nombreProd;
        @NotNull @Min(1) private Integer cantidad;
        @NotNull @DecimalMin("0.01") private BigDecimal precioUnit;
    }

    @Getter @Setter
    public static class PedidoRequest {
        @NotNull private UUID usuarioId;
        @NotNull private UUID tiendaId;
        @NotBlank private String direccionEnvio;
        private String notas;
        private BigDecimal descuento;
        @NotEmpty @Valid private List<ItemRequest> items;
    }

    @Getter @Setter
    public static class EstadoRequest {
        @NotNull private EstadoPedido estado;
    }

    @Getter @Builder
    public static class ItemResponse {
        private UUID id;
        private UUID productoId;
        private String nombreProd;
        private Integer cantidad;
        private BigDecimal precioUnit;
        private BigDecimal subtotal;

        public static ItemResponse from(ItemPedido i) {
            return ItemResponse.builder()
                    .id(i.getId()).productoId(i.getProductoId())
                    .nombreProd(i.getNombreProd()).cantidad(i.getCantidad())
                    .precioUnit(i.getPrecioUnit()).subtotal(i.getSubtotal())
                    .build();
        }
    }

    @Getter @Builder
    public static class PedidoResponse {
        private UUID id;
        private UUID usuarioId;
        private UUID tiendaId;
        private EstadoPedido estado;
        private BigDecimal subtotal;
        private BigDecimal descuento;
        private BigDecimal total;
        private String direccionEnvio;
        private String notas;
        private List<ItemResponse> items;
        private LocalDateTime createdAt;

        public static PedidoResponse from(Pedido p) {
            return PedidoResponse.builder()
                    .id(p.getId()).usuarioId(p.getUsuarioId()).tiendaId(p.getTiendaId())
                    .estado(p.getEstado()).subtotal(p.getSubtotal())
                    .descuento(p.getDescuento()).total(p.getTotal())
                    .direccionEnvio(p.getDireccionEnvio()).notas(p.getNotas())
                    .items(p.getItems().stream().map(ItemResponse::from).toList())
                    .createdAt(p.getCreatedAt())
                    .build();
        }
    }
}

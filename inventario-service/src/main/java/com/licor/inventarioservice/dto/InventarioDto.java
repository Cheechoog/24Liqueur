package com.licor.inventarioservice.dto;

import com.licor.inventarioservice.model.Inventario;
import com.licor.inventarioservice.model.MovimientoInventario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class InventarioDto {

    @Getter @Setter
    public static class InventarioRequest {
        @NotNull private UUID tiendaId;
        @NotNull private UUID productoId;
        @NotNull @Min(0) private Integer stock;
        @Min(0) private Integer stockMin;
    }

    @Getter @Setter
    public static class MovimientoRequest {
        @NotBlank @Pattern(regexp = "ENTRADA|SALIDA|AJUSTE") private String tipo;
        @NotNull @Min(1) private Integer cantidad;
        private String motivo;
        private String referencia;
    }

    @Getter @Builder
    public static class InventarioResponse {
        private UUID id;
        private UUID tiendaId;
        private UUID productoId;
        private Integer stock;
        private Integer stockMin;
        private Boolean stockBajo;
        private LocalDateTime updatedAt;

        public static InventarioResponse from(Inventario i) {
            return InventarioResponse.builder()
                    .id(i.getId()).tiendaId(i.getTiendaId()).productoId(i.getProductoId())
                    .stock(i.getStock()).stockMin(i.getStockMin())
                    .stockBajo(i.getStock() <= i.getStockMin())
                    .updatedAt(i.getUpdatedAt())
                    .build();
        }
    }

    @Getter @Builder
    public static class MovimientoResponse {
        private UUID id;
        private UUID inventarioId;
        private String tipo;
        private Integer cantidad;
        private String motivo;
        private String referencia;
        private LocalDateTime createdAt;

        public static MovimientoResponse from(MovimientoInventario m) {
            return MovimientoResponse.builder()
                    .id(m.getId()).inventarioId(m.getInventario().getId())
                    .tipo(m.getTipo()).cantidad(m.getCantidad())
                    .motivo(m.getMotivo()).referencia(m.getReferencia())
                    .createdAt(m.getCreatedAt())
                    .build();
        }
    }
}

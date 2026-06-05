package com.licor.pagosservice.dto;

import com.licor.pagosservice.model.EstadoPago;
import com.licor.pagosservice.model.MetodoPago;
import com.licor.pagosservice.model.Pago;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PagoDto {

    @Getter @Setter
    public static class PagoRequest {
        @NotNull private UUID pedidoId;
        @NotNull private UUID usuarioId;
        @NotNull @DecimalMin("0.01") private BigDecimal monto;
        @NotNull private MetodoPago metodo;
        private String referenciaExt;
    }

    @Getter @Setter
    public static class EstadoRequest {
        @NotNull private EstadoPago estado;
        private String referenciaExt;
    }

    @Getter @Builder
    public static class PagoResponse {
        private UUID id;
        private UUID pedidoId;
        private UUID usuarioId;
        private BigDecimal monto;
        private MetodoPago metodo;
        private EstadoPago estado;
        private String referenciaExt;
        private LocalDateTime createdAt;

        public static PagoResponse from(Pago p) {
            return PagoResponse.builder()
                    .id(p.getId()).pedidoId(p.getPedidoId()).usuarioId(p.getUsuarioId())
                    .monto(p.getMonto()).metodo(p.getMetodo()).estado(p.getEstado())
                    .referenciaExt(p.getReferenciaExt()).createdAt(p.getCreatedAt())
                    .build();
        }
    }
}

package com.licor.enviosservice.dto;

import com.licor.enviosservice.model.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class EnvioDto {

    @Getter @Setter
    public static class EnvioRequest {
        @NotNull private UUID pedidoId;
        @NotBlank private String direccionOrigen;
        @NotBlank private String direccionDestino;
        private BigDecimal latDestino;
        private BigDecimal lngDestino;
        private Integer tiempoEstimado;
        private UUID repartidorId;
    }

    @Getter @Setter
    public static class EstadoRequest {
        @NotNull private EstadoEnvio estado;
        private String codigoConfirm;
    }

    @Getter @Setter
    public static class RepartidorRequest {
        @NotBlank @Size(max = 200) private String nombre;
        @NotBlank @Size(max = 20) private String telefono;
        @Size(max = 50) private String vehiculo;
        @Size(max = 20) private String placa;
    }

    @Getter @Builder
    public static class EnvioResponse {
        private UUID id;
        private UUID pedidoId;
        private UUID repartidorId;
        private EstadoEnvio estado;
        private String direccionOrigen;
        private String direccionDestino;
        private BigDecimal latDestino;
        private BigDecimal lngDestino;
        private Integer tiempoEstimado;
        private String codigoConfirm;
        private LocalDateTime entregadoEn;
        private LocalDateTime createdAt;

        public static EnvioResponse from(Envio e) {
            return EnvioResponse.builder()
                    .id(e.getId()).pedidoId(e.getPedidoId()).repartidorId(e.getRepartidorId())
                    .estado(e.getEstado()).direccionOrigen(e.getDireccionOrigen())
                    .direccionDestino(e.getDireccionDestino()).latDestino(e.getLatDestino())
                    .lngDestino(e.getLngDestino()).tiempoEstimado(e.getTiempoEstimado())
                    .codigoConfirm(e.getCodigoConfirm()).entregadoEn(e.getEntregadoEn())
                    .createdAt(e.getCreatedAt()).build();
        }
    }

    @Getter @Builder
    public static class RepartidorResponse {
        private UUID id;
        private String nombre;
        private String telefono;
        private String vehiculo;
        private String placa;
        private Boolean activo;
        private BigDecimal latActual;
        private BigDecimal lngActual;

        public static RepartidorResponse from(Repartidor r) {
            return RepartidorResponse.builder()
                    .id(r.getId()).nombre(r.getNombre()).telefono(r.getTelefono())
                    .vehiculo(r.getVehiculo()).placa(r.getPlaca()).activo(r.getActivo())
                    .latActual(r.getLatActual()).lngActual(r.getLngActual()).build();
        }
    }
}

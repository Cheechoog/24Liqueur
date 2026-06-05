package com.licor.notificacionesservice.dto;

import com.licor.notificacionesservice.model.Notificacion;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificacionDto {

    @Getter @Setter
    public static class NotificacionRequest {
        @NotNull private UUID usuarioId;
        @NotNull private Notificacion.Tipo tipo;
        @Size(max = 200) private String asunto;
        @NotBlank private String mensaje;
        @Size(max = 200) private String referencia;
    }

    @Getter @Builder
    public static class NotificacionResponse {
        private UUID id;
        private UUID usuarioId;
        private Notificacion.Tipo tipo;
        private String asunto;
        private String mensaje;
        private Notificacion.Estado estado;
        private String referencia;
        private Integer intentos;
        private LocalDateTime enviadaEn;
        private LocalDateTime createdAt;

        public static NotificacionResponse from(Notificacion n) {
            return NotificacionResponse.builder()
                    .id(n.getId()).usuarioId(n.getUsuarioId()).tipo(n.getTipo())
                    .asunto(n.getAsunto()).mensaje(n.getMensaje()).estado(n.getEstado())
                    .referencia(n.getReferencia()).intentos(n.getIntentos())
                    .enviadaEn(n.getEnviadaEn()).createdAt(n.getCreatedAt())
                    .build();
        }
    }
}

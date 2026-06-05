package com.licor.tiendasservice.dto;

import com.licor.tiendasservice.model.Tienda;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class TiendaDto {

    @Getter @Setter
    public static class TiendaRequest {

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200)
        private String nombre;

        private String descripcion;

        @NotNull(message = "El propietario es obligatorio")
        private UUID propietarioId;

        @Size(max = 20)
        private String nit;

        @Size(max = 20)
        private String telefono;

        @Email(message = "Email inválido")
        @Size(max = 150)
        private String email;

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 300)
        private String direccion;

        @NotBlank(message = "La ciudad es obligatoria")
        @Size(max = 100)
        private String ciudad;

        private BigDecimal lat;
        private BigDecimal lng;
        private String logoUrl;

        private LocalTime horarioInicio;
        private LocalTime horarioFin;

        @Min(value = 1, message = "El radio mínimo es 1 km")
        @Max(value = 50, message = "El radio máximo es 50 km")
        private Integer radioEnvioKm;
    }

    @Getter @Builder
    public static class TiendaResponse {
        private UUID id;
        private String nombre;
        private String descripcion;
        private UUID propietarioId;
        private String nit;
        private String telefono;
        private String email;
        private String direccion;
        private String ciudad;
        private BigDecimal lat;
        private BigDecimal lng;
        private String logoUrl;
        private Boolean activa;
        private LocalTime horarioInicio;
        private LocalTime horarioFin;
        private Integer radioEnvioKm;
        private LocalDateTime createdAt;

        public static TiendaResponse from(Tienda t) {
            return TiendaResponse.builder()
                    .id(t.getId())
                    .nombre(t.getNombre())
                    .descripcion(t.getDescripcion())
                    .propietarioId(t.getPropietarioId())
                    .nit(t.getNit())
                    .telefono(t.getTelefono())
                    .email(t.getEmail())
                    .direccion(t.getDireccion())
                    .ciudad(t.getCiudad())
                    .lat(t.getLat())
                    .lng(t.getLng())
                    .logoUrl(t.getLogoUrl())
                    .activa(t.getActiva())
                    .horarioInicio(t.getHorarioInicio())
                    .horarioFin(t.getHorarioFin())
                    .radioEnvioKm(t.getRadioEnvioKm())
                    .createdAt(t.getCreatedAt())
                    .build();
        }
    }
}

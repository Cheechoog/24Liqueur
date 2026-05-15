package com.licor.usuariosservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.licor.usuariosservice.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioDto {

    // ── Request: registro ────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class RegisterRequest {

        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "El apellido es obligatorio")
        private String apellido;

        @Email(message = "Email inválido")
        @NotBlank(message = "El email es obligatorio")
        private String email;

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "Mínimo 8 caracteres")
        private String password;

        private String telefono;

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha debe ser en el pasado")
        private LocalDate fechaNac;
    }

    // ── Request: login ───────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    // ── Response: token ──────────────────────────────────────────────────────
    @Getter @Setter @AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private String tipo = "Bearer";
        private UsuarioResponse usuario;
    }

    // ── Response: usuario ────────────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UsuarioResponse {
        private UUID id;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private LocalDate fechaNac;
        private Usuario.Rol rol;
        private Boolean activo;
        private LocalDateTime createdAt;

        public static UsuarioResponse from(Usuario u) {
            return UsuarioResponse.builder()
                    .id(u.getId())
                    .nombre(u.getNombre())
                    .apellido(u.getApellido())
                    .email(u.getEmail())
                    .telefono(u.getTelefono())
                    .fechaNac(u.getFechaNac())
                    .rol(u.getRol())
                    .activo(u.getActivo())
                    .createdAt(u.getCreatedAt())
                    .build();
        }
    }
}

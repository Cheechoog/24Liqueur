package com.licor.catalogoservice.dto;

import com.licor.catalogoservice.model.Categoria;
import com.licor.catalogoservice.model.Producto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CatalogoDto {

    // ── Categoría ─────────────────────────────────────────────────────────────

    @Getter @Setter
    public static class CategoriaRequest {
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        private String nombre;
        private String descripcion;
    }

    @Getter @Builder
    public static class CategoriaResponse {
        private UUID id;
        private String nombre;
        private String descripcion;
        private Boolean activa;

        public static CategoriaResponse from(Categoria c) {
            return CategoriaResponse.builder()
                    .id(c.getId())
                    .nombre(c.getNombre())
                    .descripcion(c.getDescripcion())
                    .activa(c.getActiva())
                    .build();
        }
    }

    // ── Producto ──────────────────────────────────────────────────────────────

    @Getter @Setter
    public static class ProductoRequest {
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 200)
        private String nombre;

        private String descripcion;

        @NotNull(message = "La categoría es obligatoria")
        private UUID categoriaId;

        @Size(max = 100)
        private String marca;

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        private BigDecimal precio;

        private BigDecimal precioOferta;

        @DecimalMin(value = "0.0") @DecimalMax(value = "100.0")
        private BigDecimal graduacion;

        private Integer volumenMl;
        private String imagenUrl;
    }

    @Getter @Builder
    public static class ProductoResponse {
        private UUID id;
        private String nombre;
        private String descripcion;
        private UUID categoriaId;
        private String categoriaNombre;
        private String marca;
        private BigDecimal precio;
        private BigDecimal precioOferta;
        private BigDecimal graduacion;
        private Integer volumenMl;
        private String imagenUrl;
        private Boolean activo;
        private LocalDateTime createdAt;

        public static ProductoResponse from(Producto p) {
            return ProductoResponse.builder()
                    .id(p.getId())
                    .nombre(p.getNombre())
                    .descripcion(p.getDescripcion())
                    .categoriaId(p.getCategoria() != null ? p.getCategoria().getId() : null)
                    .categoriaNombre(p.getCategoria() != null ? p.getCategoria().getNombre() : null)
                    .marca(p.getMarca())
                    .precio(p.getPrecio())
                    .precioOferta(p.getPrecioOferta())
                    .graduacion(p.getGraduacion())
                    .volumenMl(p.getVolumenMl())
                    .imagenUrl(p.getImagenUrl())
                    .activo(p.getActivo())
                    .createdAt(p.getCreatedAt())
                    .build();
        }
    }
}

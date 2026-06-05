package com.licor.catalogoservice.controller;

import com.licor.catalogoservice.dto.CatalogoDto;
import com.licor.catalogoservice.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    // ════════════════════════════════════════════════════════════════════════
    // CATEGORÍAS
    // ════════════════════════════════════════════════════════════════════════

    @Tag(name = "Categorías", description = "Gestión de categorías de licores")
    @GetMapping("/categorias")
    @Operation(summary = "Listar todas las categorías activas")
    public ResponseEntity<List<CatalogoDto.CategoriaResponse>> listarCategorias() {
        return ResponseEntity.ok(catalogoService.listarCategorias());
    }

    @Tag(name = "Categorías", description = "Gestión de categorías de licores")
    @PostMapping("/categorias")
    @Operation(summary = "Crear nueva categoría (ADMIN)")
    public ResponseEntity<CatalogoDto.CategoriaResponse> crearCategoria(
            @Valid @RequestBody CatalogoDto.CategoriaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearCategoria(req));
    }

    @Tag(name = "Categorías", description = "Gestión de categorías de licores")
    @DeleteMapping("/categorias/{id}")
    @Operation(summary = "Desactivar categoría (ADMIN)")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable UUID id) {
        catalogoService.desactivarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Categorías", description = "Gestión de categorías de licores")
    @PutMapping("/categorias/{id}/activar")
    @Operation(summary = "Reactivar categoría desactivada (ADMIN)")
    public ResponseEntity<CatalogoDto.CategoriaResponse> activarCategoria(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogoService.activarCategoria(id));
    }

    // ════════════════════════════════════════════════════════════════════════
    // PRODUCTOS
    // ════════════════════════════════════════════════════════════════════════

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @GetMapping("/productos")
    @Operation(summary = "Listar todos los productos activos")
    public ResponseEntity<List<CatalogoDto.ProductoResponse>> listarProductos() {
        return ResponseEntity.ok(catalogoService.listarProductos());
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @GetMapping("/productos/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<CatalogoDto.ProductoResponse> obtenerProducto(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogoService.obtenerProducto(id));
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @GetMapping("/productos/categoria/{categoriaId}")
    @Operation(summary = "Listar productos por categoría")
    public ResponseEntity<List<CatalogoDto.ProductoResponse>> porCategoria(
            @PathVariable UUID categoriaId) {
        return ResponseEntity.ok(catalogoService.listarPorCategoria(categoriaId));
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @GetMapping("/productos/buscar")
    @Operation(summary = "Buscar productos por nombre o marca")
    public ResponseEntity<List<CatalogoDto.ProductoResponse>> buscar(
            @RequestParam String q) {
        return ResponseEntity.ok(catalogoService.buscar(q));
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @PostMapping("/productos")
    @Operation(summary = "Crear nuevo producto (ADMIN/TIENDA)")
    public ResponseEntity<CatalogoDto.ProductoResponse> crearProducto(
            @Valid @RequestBody CatalogoDto.ProductoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.crearProducto(req));
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @PutMapping("/productos/{id}")
    @Operation(summary = "Actualizar producto (ADMIN/TIENDA)")
    public ResponseEntity<CatalogoDto.ProductoResponse> actualizarProducto(
            @PathVariable UUID id,
            @Valid @RequestBody CatalogoDto.ProductoRequest req) {
        return ResponseEntity.ok(catalogoService.actualizarProducto(id, req));
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @DeleteMapping("/productos/{id}")
    @Operation(summary = "Desactivar producto (ADMIN/TIENDA)")
    public ResponseEntity<Void> desactivarProducto(@PathVariable UUID id) {
        catalogoService.desactivarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @Tag(name = "Productos", description = "Gestión del catálogo de productos")
    @PutMapping("/productos/{id}/activar")
    @Operation(summary = "Reactivar producto desactivado (ADMIN/TIENDA)")
    public ResponseEntity<CatalogoDto.ProductoResponse> activarProducto(@PathVariable UUID id) {
        return ResponseEntity.ok(catalogoService.activarProducto(id));
    }
}

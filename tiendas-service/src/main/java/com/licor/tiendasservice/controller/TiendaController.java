package com.licor.tiendasservice.controller;

import com.licor.tiendasservice.dto.TiendaDto;
import com.licor.tiendasservice.service.TiendaService;
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
@RequestMapping("/tiendas")
@RequiredArgsConstructor
@Tag(name = "Tiendas", description = "Gestión de tiendas de licores")
public class TiendaController {

    private final TiendaService tiendaService;

    @GetMapping
    @Operation(summary = "Listar todas las tiendas activas")
    public ResponseEntity<List<TiendaDto.TiendaResponse>> listar() {
        return ResponseEntity.ok(tiendaService.listarTiendas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tienda por ID")
    public ResponseEntity<TiendaDto.TiendaResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(tiendaService.obtenerTienda(id));
    }

    @GetMapping("/ciudad/{ciudad}")
    @Operation(summary = "Listar tiendas por ciudad")
    public ResponseEntity<List<TiendaDto.TiendaResponse>> porCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(tiendaService.listarPorCiudad(ciudad));
    }

    @GetMapping("/propietario/{propietarioId}")
    @Operation(summary = "Listar tiendas de un propietario")
    public ResponseEntity<List<TiendaDto.TiendaResponse>> porPropietario(@PathVariable UUID propietarioId) {
        return ResponseEntity.ok(tiendaService.listarPorPropietario(propietarioId));
    }

    @PostMapping
    @Operation(summary = "Registrar nueva tienda")
    public ResponseEntity<TiendaDto.TiendaResponse> crear(@Valid @RequestBody TiendaDto.TiendaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tiendaService.crearTienda(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de la tienda")
    public ResponseEntity<TiendaDto.TiendaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody TiendaDto.TiendaRequest req) {
        return ResponseEntity.ok(tiendaService.actualizarTienda(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar tienda")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        tiendaService.desactivarTienda(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @Operation(summary = "Reactivar tienda desactivada (ADMIN)")
    public ResponseEntity<TiendaDto.TiendaResponse> activar(@PathVariable UUID id) {
        return ResponseEntity.ok(tiendaService.activarTienda(id));
    }
}

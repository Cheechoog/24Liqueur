package com.licor.inventarioservice.controller;

import com.licor.inventarioservice.dto.InventarioDto;
import com.licor.inventarioservice.service.InventarioService;
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
@RequestMapping("/inventario")
@RequiredArgsConstructor
@Tag(name = "Inventario", description = "Gestión de inventario por tienda")
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping("/tienda/{tiendaId}")
    @Operation(summary = "Ver inventario completo de una tienda")
    public ResponseEntity<List<InventarioDto.InventarioResponse>> porTienda(@PathVariable UUID tiendaId) {
        return ResponseEntity.ok(inventarioService.porTienda(tiendaId));
    }

    @GetMapping("/tienda/{tiendaId}/stock-bajo")
    @Operation(summary = "Productos con stock bajo en una tienda")
    public ResponseEntity<List<InventarioDto.InventarioResponse>> stockBajo(@PathVariable UUID tiendaId) {
        return ResponseEntity.ok(inventarioService.stockBajo(tiendaId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener item de inventario por ID")
    public ResponseEntity<InventarioDto.InventarioResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(inventarioService.obtener(id));
    }

    @GetMapping("/{id}/movimientos")
    @Operation(summary = "Historial de movimientos de un item")
    public ResponseEntity<List<InventarioDto.MovimientoResponse>> movimientos(@PathVariable UUID id) {
        return ResponseEntity.ok(inventarioService.movimientos(id));
    }

    @PostMapping
    @Operation(summary = "Registrar producto en inventario de tienda")
    public ResponseEntity<InventarioDto.InventarioResponse> crear(
            @Valid @RequestBody InventarioDto.InventarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crear(req));
    }

    @PostMapping("/{id}/movimiento")
    @Operation(summary = "Registrar entrada, salida o ajuste de stock")
    public ResponseEntity<InventarioDto.InventarioResponse> movimiento(
            @PathVariable UUID id,
            @Valid @RequestBody InventarioDto.MovimientoRequest req) {
        return ResponseEntity.ok(inventarioService.registrarMovimiento(id, req));
    }
}

package com.licor.enviosservice.controller;

import com.licor.enviosservice.dto.EnvioDto;
import com.licor.enviosservice.service.EnvioService;
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
public class EnvioController {

    private final EnvioService envioService;

    // ── Envíos ────────────────────────────────────────────────────────────────

    @Tag(name = "Envíos", description = "Seguimiento y gestión de envíos")
    @GetMapping("/envios/{id}")
    @Operation(summary = "Obtener envío por ID")
    public ResponseEntity<EnvioDto.EnvioResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(envioService.obtener(id));
    }

    @Tag(name = "Envíos", description = "Seguimiento y gestión de envíos")
    @GetMapping("/envios/pedido/{pedidoId}")
    @Operation(summary = "Obtener envío de un pedido")
    public ResponseEntity<EnvioDto.EnvioResponse> porPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(envioService.porPedido(pedidoId));
    }

    @Tag(name = "Envíos", description = "Seguimiento y gestión de envíos")
    @PostMapping("/envios")
    @Operation(summary = "Crear envío para un pedido")
    public ResponseEntity<EnvioDto.EnvioResponse> crear(@Valid @RequestBody EnvioDto.EnvioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.crear(req));
    }

    @Tag(name = "Envíos", description = "Seguimiento y gestión de envíos")
    @PutMapping("/envios/{id}/estado")
    @Operation(summary = "Actualizar estado del envío")
    public ResponseEntity<EnvioDto.EnvioResponse> actualizarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody EnvioDto.EstadoRequest req) {
        return ResponseEntity.ok(envioService.actualizarEstado(id, req));
    }

    @Tag(name = "Envíos", description = "Seguimiento y gestión de envíos")
    @PutMapping("/envios/{id}/repartidor/{repartidorId}")
    @Operation(summary = "Asignar repartidor a un envío")
    public ResponseEntity<EnvioDto.EnvioResponse> asignarRepartidor(
            @PathVariable UUID id, @PathVariable UUID repartidorId) {
        return ResponseEntity.ok(envioService.asignarRepartidor(id, repartidorId));
    }

    // ── Repartidores ──────────────────────────────────────────────────────────

    @Tag(name = "Repartidores", description = "Gestión de repartidores")
    @GetMapping("/repartidores")
    @Operation(summary = "Listar repartidores activos")
    public ResponseEntity<List<EnvioDto.RepartidorResponse>> listarRepartidores() {
        return ResponseEntity.ok(envioService.listarRepartidores());
    }

    @Tag(name = "Repartidores", description = "Gestión de repartidores")
    @PostMapping("/repartidores")
    @Operation(summary = "Registrar nuevo repartidor")
    public ResponseEntity<EnvioDto.RepartidorResponse> crearRepartidor(
            @Valid @RequestBody EnvioDto.RepartidorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.crearRepartidor(req));
    }
}

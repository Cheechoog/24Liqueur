package com.licor.pagosservice.controller;

import com.licor.pagosservice.dto.PagoDto;
import com.licor.pagosservice.service.PagoService;
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
@RequestMapping("/pagos")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestión de pagos del marketplace")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID")
    public ResponseEntity<PagoDto.PagoResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(pagoService.obtener(id));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Obtener pago de un pedido")
    public ResponseEntity<PagoDto.PagoResponse> porPedido(@PathVariable UUID pedidoId) {
        return ResponseEntity.ok(pagoService.obtenerPorPedido(pedidoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Historial de pagos de un usuario")
    public ResponseEntity<List<PagoDto.PagoResponse>> porUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(pagoService.porUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Registrar nuevo pago")
    public ResponseEntity<PagoDto.PagoResponse> crear(@Valid @RequestBody PagoDto.PagoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crear(req));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar estado del pago (webhook del proveedor)")
    public ResponseEntity<PagoDto.PagoResponse> actualizarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody PagoDto.EstadoRequest req) {
        return ResponseEntity.ok(pagoService.actualizarEstado(id, req));
    }

    @PutMapping("/{id}/reembolsar")
    @Operation(summary = "Reembolsar pago aprobado (ADMIN)")
    public ResponseEntity<PagoDto.PagoResponse> reembolsar(@PathVariable UUID id) {
        return ResponseEntity.ok(pagoService.reembolsar(id));
    }
}

package com.licor.pedidosservice.controller;

import com.licor.pedidosservice.dto.PedidoDto;
import com.licor.pedidosservice.model.EstadoPedido;
import com.licor.pedidosservice.service.PedidoService;
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
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gestión de pedidos del marketplace")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos (ADMIN)")
    public ResponseEntity<List<PedidoDto.PedidoResponse>> listar() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    public ResponseEntity<PedidoDto.PedidoResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.obtener(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Pedidos de un usuario")
    public ResponseEntity<List<PedidoDto.PedidoResponse>> porUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(pedidoService.porUsuario(usuarioId));
    }

    @GetMapping("/tienda/{tiendaId}")
    @Operation(summary = "Pedidos de una tienda")
    public ResponseEntity<List<PedidoDto.PedidoResponse>> porTienda(@PathVariable UUID tiendaId) {
        return ResponseEntity.ok(pedidoService.porTienda(tiendaId));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido")
    public ResponseEntity<PedidoDto.PedidoResponse> crear(@Valid @RequestBody PedidoDto.PedidoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crear(req));
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado del pedido (TIENDA/ADMIN)")
    public ResponseEntity<PedidoDto.PedidoResponse> cambiarEstado(
            @PathVariable UUID id,
            @Valid @RequestBody PedidoDto.EstadoRequest req) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, req.getEstado()));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido")
    public ResponseEntity<PedidoDto.PedidoResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(pedidoService.cancelar(id));
    }
}

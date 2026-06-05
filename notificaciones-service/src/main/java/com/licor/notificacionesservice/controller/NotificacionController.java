package com.licor.notificacionesservice.controller;

import com.licor.notificacionesservice.dto.NotificacionDto;
import com.licor.notificacionesservice.service.NotificacionService;
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
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Gestión de notificaciones del sistema")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Notificaciones de un usuario")
    public ResponseEntity<List<NotificacionDto.NotificacionResponse>> porUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(notificacionService.porUsuario(usuarioId));
    }

    @GetMapping("/pendientes")
    @Operation(summary = "Listar notificaciones pendientes de envío (SYSTEM)")
    public ResponseEntity<List<NotificacionDto.NotificacionResponse>> pendientes() {
        return ResponseEntity.ok(notificacionService.pendientes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID")
    public ResponseEntity<NotificacionDto.NotificacionResponse> obtener(@PathVariable UUID id) {
        return ResponseEntity.ok(notificacionService.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear nueva notificación")
    public ResponseEntity<NotificacionDto.NotificacionResponse> crear(
            @Valid @RequestBody NotificacionDto.NotificacionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(req));
    }

    @PutMapping("/{id}/enviada")
    @Operation(summary = "Marcar notificación como enviada")
    public ResponseEntity<NotificacionDto.NotificacionResponse> marcarEnviada(@PathVariable UUID id) {
        return ResponseEntity.ok(notificacionService.marcarEnviada(id));
    }

    @PutMapping("/{id}/fallida")
    @Operation(summary = "Marcar notificación como fallida")
    public ResponseEntity<NotificacionDto.NotificacionResponse> marcarFallida(@PathVariable UUID id) {
        return ResponseEntity.ok(notificacionService.marcarFallida(id));
    }

    @PutMapping("/{id}/reintentar")
    @Operation(summary = "Reintentar notificación fallida")
    public ResponseEntity<NotificacionDto.NotificacionResponse> reintentar(@PathVariable UUID id) {
        return ResponseEntity.ok(notificacionService.reintentar(id));
    }
}

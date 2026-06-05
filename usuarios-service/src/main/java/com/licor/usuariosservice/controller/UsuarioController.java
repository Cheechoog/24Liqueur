package com.licor.usuariosservice.controller;

import com.licor.usuariosservice.dto.UsuarioDto;
import com.licor.usuariosservice.service.UsuarioService;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Registro, login y gestión de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ── POST /auth/register ───────────────────────────────────────────────────
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nuevo usuario (mayores de 18)")
    public ResponseEntity<UsuarioDto.AuthResponse> register(
            @Valid @RequestBody UsuarioDto.RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.register(req));
    }

    // ── POST /auth/login ──────────────────────────────────────────────────────
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener JWT")
    public ResponseEntity<UsuarioDto.AuthResponse> login(
            @Valid @RequestBody UsuarioDto.LoginRequest req) {
        return ResponseEntity.ok(usuarioService.login(req));
    }

    // ── GET /auth/usuarios/{id} ───────────────────────────────────────────────
    @GetMapping("/usuarios/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public ResponseEntity<UsuarioDto.UsuarioResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    // ── GET /auth/usuarios ────────────────────────────────────────────────────
    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios (ADMIN)")
    public ResponseEntity<List<UsuarioDto.UsuarioResponse>> getAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // ── DELETE /auth/usuarios/{id} ────────────────────────────────────────────
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Desactivar cuenta de usuario")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        usuarioService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    // ── PUT /auth/usuarios/{id}/activar ───────────────────────────────────────
    @PutMapping("/usuarios/{id}/activar")
    @Operation(summary = "Reactivar cuenta de usuario desactivada (ADMIN)")
    public ResponseEntity<UsuarioDto.UsuarioResponse> activate(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.activate(id));
    }

}

package com.licor.usuariosservice.service;

import com.licor.usuariosservice.config.JwtUtil;
import com.licor.usuariosservice.dto.UsuarioDto;
import com.licor.usuariosservice.model.Usuario;
import com.licor.usuariosservice.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;

    // ── Registro ─────────────────────────────────────────────────────────────
    @Transactional
    public UsuarioDto.AuthResponse register(UsuarioDto.RegisterRequest req) {

        // Validar mayoría de edad (18+)
        int edad = Period.between(req.getFechaNac(), LocalDate.now()).getYears();
        if (edad < 18) {
            throw new IllegalArgumentException("Debes ser mayor de 18 años para registrarte.");
        }

        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        Usuario usuario = Usuario.builder()
                .nombre(req.getNombre())
                .apellido(req.getApellido())
                .email(req.getEmail().toLowerCase())
                .password(passwordEncoder.encode(req.getPassword()))
                .telefono(req.getTelefono())
                .fechaNac(req.getFechaNac())
                .rol(Usuario.Rol.CLIENTE)
                .activo(true)
                .build();

        usuarioRepository.save(usuario);
        log.info("Nuevo usuario registrado: {}", usuario.getEmail());

        // Publicar evento a RabbitMQ
        rabbitTemplate.convertAndSend(
            "licor.exchange", "usuario.registered",
            "usuario_id:" + usuario.getId()
        );

        String token = jwtUtil.generateToken(usuario.getId().toString(), usuario.getRol().name());
        return new UsuarioDto.AuthResponse(token, "Bearer", UsuarioDto.UsuarioResponse.from(usuario));
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    public UsuarioDto.AuthResponse login(UsuarioDto.LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(req.getEmail().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas."));

        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("Cuenta desactivada. Contacta soporte.");
        }

        if (!passwordEncoder.matches(req.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas.");
        }

        String token = jwtUtil.generateToken(usuario.getId().toString(), usuario.getRol().name());
        log.info("Login exitoso: {}", usuario.getEmail());
        return new UsuarioDto.AuthResponse(token, "Bearer", UsuarioDto.UsuarioResponse.from(usuario));
    }

    // ── Obtener por ID ────────────────────────────────────────────────────────
    public UsuarioDto.UsuarioResponse findById(UUID id) {
        return usuarioRepository.findById(id)
                .map(UsuarioDto.UsuarioResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
    }

    // ── Listar todos (solo ADMIN) ─────────────────────────────────────────────
    public List<UsuarioDto.UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDto.UsuarioResponse::from)
                .toList();
    }

    // ── Desactivar cuenta ─────────────────────────────────────────────────────
    @Transactional
    public void deactivate(UUID id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        u.setActivo(false);
        usuarioRepository.save(u);
    }
}

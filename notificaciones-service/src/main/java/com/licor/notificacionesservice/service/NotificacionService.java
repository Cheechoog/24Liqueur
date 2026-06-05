package com.licor.notificacionesservice.service;

import com.licor.notificacionesservice.dto.NotificacionDto;
import com.licor.notificacionesservice.model.Notificacion;
import com.licor.notificacionesservice.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public List<NotificacionDto.NotificacionResponse> porUsuario(UUID usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                .stream().map(NotificacionDto.NotificacionResponse::from).toList();
    }

    public List<NotificacionDto.NotificacionResponse> pendientes() {
        return notificacionRepository.findByEstadoOrderByCreatedAtAsc(Notificacion.Estado.PENDIENTE)
                .stream().map(NotificacionDto.NotificacionResponse::from).toList();
    }

    public NotificacionDto.NotificacionResponse obtener(UUID id) {
        return notificacionRepository.findById(id)
                .map(NotificacionDto.NotificacionResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada."));
    }

    @Transactional
    public NotificacionDto.NotificacionResponse crear(NotificacionDto.NotificacionRequest req) {
        Notificacion n = Notificacion.builder()
                .usuarioId(req.getUsuarioId()).tipo(req.getTipo())
                .asunto(req.getAsunto()).mensaje(req.getMensaje())
                .referencia(req.getReferencia()).estado(Notificacion.Estado.PENDIENTE)
                .intentos(0).build();
        log.info("Notificación creada tipo {} para usuario {}", req.getTipo(), req.getUsuarioId());
        return NotificacionDto.NotificacionResponse.from(notificacionRepository.save(n));
    }

    @Transactional
    public NotificacionDto.NotificacionResponse marcarEnviada(UUID id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada."));
        n.setEstado(Notificacion.Estado.ENVIADA);
        n.setEnviadaEn(LocalDateTime.now());
        n.setIntentos(n.getIntentos() + 1);
        return NotificacionDto.NotificacionResponse.from(notificacionRepository.save(n));
    }

    @Transactional
    public NotificacionDto.NotificacionResponse marcarFallida(UUID id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada."));
        n.setEstado(Notificacion.Estado.FALLIDA);
        n.setIntentos(n.getIntentos() + 1);
        return NotificacionDto.NotificacionResponse.from(notificacionRepository.save(n));
    }

    @Transactional
    public NotificacionDto.NotificacionResponse reintentar(UUID id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada."));
        if (n.getEstado() != Notificacion.Estado.FALLIDA) {
            throw new IllegalArgumentException("Solo se pueden reintentar notificaciones fallidas.");
        }
        n.setEstado(Notificacion.Estado.PENDIENTE);
        return NotificacionDto.NotificacionResponse.from(notificacionRepository.save(n));
    }
}

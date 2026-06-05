package com.licor.notificacionesservice.repository;

import com.licor.notificacionesservice.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificacionRepository extends JpaRepository<Notificacion, UUID> {
    List<Notificacion> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
    List<Notificacion> findByEstadoOrderByCreatedAtAsc(Notificacion.Estado estado);
}

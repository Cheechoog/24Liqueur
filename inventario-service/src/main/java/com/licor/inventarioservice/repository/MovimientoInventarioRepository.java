package com.licor.inventarioservice.repository;

import com.licor.inventarioservice.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, UUID> {
    List<MovimientoInventario> findByInventarioIdOrderByCreatedAtDesc(UUID inventarioId);
}

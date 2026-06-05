package com.licor.pagosservice.repository;

import com.licor.pagosservice.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
    Optional<Pago> findByPedidoId(UUID pedidoId);
    List<Pago> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
}

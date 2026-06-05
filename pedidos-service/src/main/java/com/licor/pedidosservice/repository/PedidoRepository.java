package com.licor.pedidosservice.repository;

import com.licor.pedidosservice.model.EstadoPedido;
import com.licor.pedidosservice.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    List<Pedido> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
    List<Pedido> findByTiendaIdOrderByCreatedAtDesc(UUID tiendaId);
    List<Pedido> findByEstadoOrderByCreatedAtDesc(EstadoPedido estado);
}

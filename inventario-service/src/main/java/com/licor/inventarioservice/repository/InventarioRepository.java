package com.licor.inventarioservice.repository;

import com.licor.inventarioservice.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventarioRepository extends JpaRepository<Inventario, UUID> {
    List<Inventario> findByTiendaId(UUID tiendaId);
    Optional<Inventario> findByTiendaIdAndProductoId(UUID tiendaId, UUID productoId);
    List<Inventario> findByTiendaIdAndStockLessThanEqual(UUID tiendaId, int stock);
}

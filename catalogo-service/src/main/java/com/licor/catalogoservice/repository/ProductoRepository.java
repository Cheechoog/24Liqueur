package com.licor.catalogoservice.repository;

import com.licor.catalogoservice.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoriaIdAndActivoTrue(UUID categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.activo = true AND " +
           "(LOWER(p.nombre) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           " LOWER(p.marca)  LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Producto> buscar(@Param("q") String query);
}

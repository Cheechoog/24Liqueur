package com.licor.tiendasservice.repository;

import com.licor.tiendasservice.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TiendaRepository extends JpaRepository<Tienda, UUID> {
    List<Tienda> findByActivaTrue();
    List<Tienda> findByCiudadIgnoreCaseAndActivaTrue(String ciudad);
    List<Tienda> findByPropietarioIdAndActivaTrue(UUID propietarioId);
    boolean existsByNit(String nit);
}

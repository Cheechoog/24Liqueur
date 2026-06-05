package com.licor.enviosservice.repository;

import com.licor.enviosservice.model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepartidorRepository extends JpaRepository<Repartidor, UUID> {
    List<Repartidor> findByActivoTrue();
}

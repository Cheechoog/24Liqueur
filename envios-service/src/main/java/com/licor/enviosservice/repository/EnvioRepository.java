package com.licor.enviosservice.repository;

import com.licor.enviosservice.model.Envio;
import com.licor.enviosservice.model.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnvioRepository extends JpaRepository<Envio, UUID> {
    Optional<Envio> findByPedidoId(UUID pedidoId);
    List<Envio> findByRepartidorIdAndEstadoNot(UUID repartidorId, EstadoEnvio estado);
    List<Envio> findByEstado(EstadoEnvio estado);
}

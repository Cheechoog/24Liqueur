package com.licor.enviosservice.service;

import com.licor.enviosservice.dto.EnvioDto;
import com.licor.enviosservice.model.*;
import com.licor.enviosservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final RepartidorRepository repartidorRepository;

    public EnvioDto.EnvioResponse porPedido(UUID pedidoId) {
        return envioRepository.findByPedidoId(pedidoId)
                .map(EnvioDto.EnvioResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado para ese pedido."));
    }

    public EnvioDto.EnvioResponse obtener(UUID id) {
        return envioRepository.findById(id)
                .map(EnvioDto.EnvioResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado."));
    }

    @Transactional
    public EnvioDto.EnvioResponse crear(EnvioDto.EnvioRequest req) {
        if (envioRepository.findByPedidoId(req.getPedidoId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un envío para ese pedido.");
        }
        String codigo = String.format("%06d", new Random().nextInt(999999));
        Envio envio = Envio.builder()
                .pedidoId(req.getPedidoId()).repartidorId(req.getRepartidorId())
                .direccionOrigen(req.getDireccionOrigen()).direccionDestino(req.getDireccionDestino())
                .latDestino(req.getLatDestino()).lngDestino(req.getLngDestino())
                .tiempoEstimado(req.getTiempoEstimado()).codigoConfirm(codigo)
                .estado(EstadoEnvio.ASIGNADO)
                .build();
        log.info("Envío creado para pedido: {} con código: {}", req.getPedidoId(), codigo);
        return EnvioDto.EnvioResponse.from(envioRepository.save(envio));
    }

    @Transactional
    public EnvioDto.EnvioResponse actualizarEstado(UUID id, EnvioDto.EstadoRequest req) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado."));
        envio.setEstado(req.getEstado());
        if (req.getEstado() == EstadoEnvio.ENTREGADO) {
            envio.setEntregadoEn(LocalDateTime.now());
        }
        return EnvioDto.EnvioResponse.from(envioRepository.save(envio));
    }

    @Transactional
    public EnvioDto.EnvioResponse asignarRepartidor(UUID id, UUID repartidorId) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado."));
        repartidorRepository.findById(repartidorId)
                .orElseThrow(() -> new IllegalArgumentException("Repartidor no encontrado."));
        envio.setRepartidorId(repartidorId);
        return EnvioDto.EnvioResponse.from(envioRepository.save(envio));
    }

    // ── Repartidores ──────────────────────────────────────────────────────────

    public List<EnvioDto.RepartidorResponse> listarRepartidores() {
        return repartidorRepository.findByActivoTrue().stream()
                .map(EnvioDto.RepartidorResponse::from).toList();
    }

    @Transactional
    public EnvioDto.RepartidorResponse crearRepartidor(EnvioDto.RepartidorRequest req) {
        Repartidor r = Repartidor.builder()
                .nombre(req.getNombre()).telefono(req.getTelefono())
                .vehiculo(req.getVehiculo()).placa(req.getPlaca())
                .activo(true).build();
        return EnvioDto.RepartidorResponse.from(repartidorRepository.save(r));
    }
}

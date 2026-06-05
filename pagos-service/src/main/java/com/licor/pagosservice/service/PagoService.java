package com.licor.pagosservice.service;

import com.licor.pagosservice.dto.PagoDto;
import com.licor.pagosservice.model.EstadoPago;
import com.licor.pagosservice.model.Pago;
import com.licor.pagosservice.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoDto.PagoResponse obtenerPorPedido(UUID pedidoId) {
        return pagoRepository.findByPedidoId(pedidoId)
                .map(PagoDto.PagoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado para ese pedido."));
    }

    public List<PagoDto.PagoResponse> porUsuario(UUID usuarioId) {
        return pagoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                .stream().map(PagoDto.PagoResponse::from).toList();
    }

    public PagoDto.PagoResponse obtener(UUID id) {
        return pagoRepository.findById(id)
                .map(PagoDto.PagoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado."));
    }

    @Transactional
    public PagoDto.PagoResponse crear(PagoDto.PagoRequest req) {
        if (pagoRepository.findByPedidoId(req.getPedidoId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un pago para ese pedido.");
        }
        Pago pago = Pago.builder()
                .pedidoId(req.getPedidoId()).usuarioId(req.getUsuarioId())
                .monto(req.getMonto()).metodo(req.getMetodo())
                .referenciaExt(req.getReferenciaExt())
                .estado(EstadoPago.PENDIENTE)
                .build();
        log.info("Pago creado para pedido: {}", req.getPedidoId());
        return PagoDto.PagoResponse.from(pagoRepository.save(pago));
    }

    @Transactional
    public PagoDto.PagoResponse actualizarEstado(UUID id, PagoDto.EstadoRequest req) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado."));
        pago.setEstado(req.getEstado());
        if (req.getReferenciaExt() != null) pago.setReferenciaExt(req.getReferenciaExt());
        log.info("Pago {} actualizado a: {}", id, req.getEstado());
        return PagoDto.PagoResponse.from(pagoRepository.save(pago));
    }

    @Transactional
    public PagoDto.PagoResponse reembolsar(UUID id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado."));
        if (pago.getEstado() != EstadoPago.APROBADO) {
            throw new IllegalArgumentException("Solo se pueden reembolsar pagos aprobados.");
        }
        pago.setEstado(EstadoPago.REEMBOLSADO);
        return PagoDto.PagoResponse.from(pagoRepository.save(pago));
    }
}

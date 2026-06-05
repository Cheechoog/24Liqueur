package com.licor.pedidosservice.service;

import com.licor.pedidosservice.dto.PedidoDto;
import com.licor.pedidosservice.model.*;
import com.licor.pedidosservice.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoDto.PedidoResponse> listarTodos() {
        return pedidoRepository.findAll().stream().map(PedidoDto.PedidoResponse::from).toList();
    }

    public List<PedidoDto.PedidoResponse> porUsuario(UUID usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                .stream().map(PedidoDto.PedidoResponse::from).toList();
    }

    public List<PedidoDto.PedidoResponse> porTienda(UUID tiendaId) {
        return pedidoRepository.findByTiendaIdOrderByCreatedAtDesc(tiendaId)
                .stream().map(PedidoDto.PedidoResponse::from).toList();
    }

    public PedidoDto.PedidoResponse obtener(UUID id) {
        return pedidoRepository.findById(id)
                .map(PedidoDto.PedidoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado."));
    }

    @Transactional
    public PedidoDto.PedidoResponse crear(PedidoDto.PedidoRequest req) {
        BigDecimal subtotal = req.getItems().stream()
                .map(i -> i.getPrecioUnit().multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal descuento = req.getDescuento() != null ? req.getDescuento() : BigDecimal.ZERO;
        BigDecimal total = subtotal.subtract(descuento);

        Pedido pedido = Pedido.builder()
                .usuarioId(req.getUsuarioId()).tiendaId(req.getTiendaId())
                .direccionEnvio(req.getDireccionEnvio()).notas(req.getNotas())
                .subtotal(subtotal).descuento(descuento).total(total)
                .estado(EstadoPedido.PENDIENTE)
                .build();

        List<ItemPedido> items = req.getItems().stream().map(i -> ItemPedido.builder()
                .pedido(pedido).productoId(i.getProductoId())
                .nombreProd(i.getNombreProd()).cantidad(i.getCantidad())
                .precioUnit(i.getPrecioUnit())
                .subtotal(i.getPrecioUnit().multiply(BigDecimal.valueOf(i.getCantidad())))
                .build()).toList();

        pedido.setItems(items);
        log.info("Pedido creado para usuario: {}", req.getUsuarioId());
        return PedidoDto.PedidoResponse.from(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoDto.PedidoResponse cambiarEstado(UUID id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado."));
        if (pedido.getEstado() == EstadoPedido.ENTREGADO || pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new IllegalArgumentException("No se puede cambiar el estado de un pedido finalizado.");
        }
        pedido.setEstado(nuevoEstado);
        log.info("Pedido {} cambió a estado: {}", id, nuevoEstado);
        return PedidoDto.PedidoResponse.from(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoDto.PedidoResponse cancelar(UUID id) {
        return cambiarEstado(id, EstadoPedido.CANCELADO);
    }
}

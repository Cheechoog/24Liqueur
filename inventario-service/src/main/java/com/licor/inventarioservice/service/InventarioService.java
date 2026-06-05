package com.licor.inventarioservice.service;

import com.licor.inventarioservice.dto.InventarioDto;
import com.licor.inventarioservice.model.Inventario;
import com.licor.inventarioservice.model.MovimientoInventario;
import com.licor.inventarioservice.repository.InventarioRepository;
import com.licor.inventarioservice.repository.MovimientoInventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final MovimientoInventarioRepository movimientoRepository;

    public List<InventarioDto.InventarioResponse> porTienda(UUID tiendaId) {
        return inventarioRepository.findByTiendaId(tiendaId)
                .stream().map(InventarioDto.InventarioResponse::from).toList();
    }

    public List<InventarioDto.InventarioResponse> stockBajo(UUID tiendaId) {
        return inventarioRepository.findByTiendaId(tiendaId).stream()
                .filter(i -> i.getStock() <= i.getStockMin())
                .map(InventarioDto.InventarioResponse::from).toList();
    }

    public InventarioDto.InventarioResponse obtener(UUID id) {
        return inventarioRepository.findById(id)
                .map(InventarioDto.InventarioResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado."));
    }

    @Transactional
    public InventarioDto.InventarioResponse crear(InventarioDto.InventarioRequest req) {
        inventarioRepository.findByTiendaIdAndProductoId(req.getTiendaId(), req.getProductoId())
                .ifPresent(i -> { throw new IllegalArgumentException("Ya existe inventario para ese producto en esa tienda."); });

        Inventario inv = Inventario.builder()
                .tiendaId(req.getTiendaId()).productoId(req.getProductoId())
                .stock(req.getStock()).stockMin(req.getStockMin() != null ? req.getStockMin() : 5)
                .build();
        return InventarioDto.InventarioResponse.from(inventarioRepository.save(inv));
    }

    @Transactional
    public InventarioDto.InventarioResponse registrarMovimiento(UUID id, InventarioDto.MovimientoRequest req) {
        Inventario inv = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventario no encontrado."));

        int nuevoStock = switch (req.getTipo()) {
            case "ENTRADA" -> inv.getStock() + req.getCantidad();
            case "SALIDA" -> {
                if (inv.getStock() < req.getCantidad())
                    throw new IllegalArgumentException("Stock insuficiente.");
                yield inv.getStock() - req.getCantidad();
            }
            case "AJUSTE" -> req.getCantidad();
            default -> throw new IllegalArgumentException("Tipo de movimiento inválido.");
        };

        inv.setStock(nuevoStock);
        inventarioRepository.save(inv);

        MovimientoInventario mov = MovimientoInventario.builder()
                .inventario(inv).tipo(req.getTipo()).cantidad(req.getCantidad())
                .motivo(req.getMotivo()).referencia(req.getReferencia())
                .build();
        movimientoRepository.save(mov);

        if (nuevoStock <= inv.getStockMin()) {
            log.warn("⚠️ Stock bajo en inventario {}: {} unidades", id, nuevoStock);
        }

        return InventarioDto.InventarioResponse.from(inv);
    }

    public List<InventarioDto.MovimientoResponse> movimientos(UUID id) {
        return movimientoRepository.findByInventarioIdOrderByCreatedAtDesc(id)
                .stream().map(InventarioDto.MovimientoResponse::from).toList();
    }
}

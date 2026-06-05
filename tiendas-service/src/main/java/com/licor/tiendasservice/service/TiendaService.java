package com.licor.tiendasservice.service;

import com.licor.tiendasservice.dto.TiendaDto;
import com.licor.tiendasservice.model.Tienda;
import com.licor.tiendasservice.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TiendaService {

    private final TiendaRepository tiendaRepository;

    public List<TiendaDto.TiendaResponse> listarTiendas() {
        return tiendaRepository.findByActivaTrue().stream()
                .map(TiendaDto.TiendaResponse::from)
                .toList();
    }

    public List<TiendaDto.TiendaResponse> listarPorCiudad(String ciudad) {
        return tiendaRepository.findByCiudadIgnoreCaseAndActivaTrue(ciudad).stream()
                .map(TiendaDto.TiendaResponse::from)
                .toList();
    }

    public List<TiendaDto.TiendaResponse> listarPorPropietario(UUID propietarioId) {
        return tiendaRepository.findByPropietarioIdAndActivaTrue(propietarioId).stream()
                .map(TiendaDto.TiendaResponse::from)
                .toList();
    }

    public TiendaDto.TiendaResponse obtenerTienda(UUID id) {
        return tiendaRepository.findById(id)
                .map(TiendaDto.TiendaResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada."));
    }

    @Transactional
    public TiendaDto.TiendaResponse crearTienda(TiendaDto.TiendaRequest req) {
        if (req.getNit() != null && tiendaRepository.existsByNit(req.getNit())) {
            throw new IllegalArgumentException("Ya existe una tienda con ese NIT.");
        }

        Tienda t = Tienda.builder()
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .propietarioId(req.getPropietarioId())
                .nit(req.getNit())
                .telefono(req.getTelefono())
                .email(req.getEmail())
                .direccion(req.getDireccion())
                .ciudad(req.getCiudad())
                .lat(req.getLat())
                .lng(req.getLng())
                .logoUrl(req.getLogoUrl())
                .horarioInicio(req.getHorarioInicio() != null ? req.getHorarioInicio() : java.time.LocalTime.of(0, 0))
                .horarioFin(req.getHorarioFin() != null ? req.getHorarioFin() : java.time.LocalTime.of(23, 59))
                .radioEnvioKm(req.getRadioEnvioKm() != null ? req.getRadioEnvioKm() : 5)
                .activa(true)
                .build();

        log.info("Tienda creada: {}", t.getNombre());
        return TiendaDto.TiendaResponse.from(tiendaRepository.save(t));
    }

    @Transactional
    public TiendaDto.TiendaResponse actualizarTienda(UUID id, TiendaDto.TiendaRequest req) {
        Tienda t = tiendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada."));

        t.setNombre(req.getNombre());
        t.setDescripcion(req.getDescripcion());
        t.setTelefono(req.getTelefono());
        t.setEmail(req.getEmail());
        t.setDireccion(req.getDireccion());
        t.setCiudad(req.getCiudad());
        t.setLat(req.getLat());
        t.setLng(req.getLng());
        t.setLogoUrl(req.getLogoUrl());
        if (req.getHorarioInicio() != null) t.setHorarioInicio(req.getHorarioInicio());
        if (req.getHorarioFin() != null) t.setHorarioFin(req.getHorarioFin());
        if (req.getRadioEnvioKm() != null) t.setRadioEnvioKm(req.getRadioEnvioKm());

        return TiendaDto.TiendaResponse.from(tiendaRepository.save(t));
    }

    @Transactional
    public void desactivarTienda(UUID id) {
        Tienda t = tiendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada."));
        t.setActiva(false);
        tiendaRepository.save(t);
        log.info("Tienda desactivada: {}", t.getNombre());
    }

    @Transactional
    public TiendaDto.TiendaResponse activarTienda(UUID id) {
        Tienda t = tiendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tienda no encontrada."));
        if (t.getActiva()) {
            throw new IllegalArgumentException("La tienda ya está activa.");
        }
        t.setActiva(true);
        tiendaRepository.save(t);
        log.info("Tienda reactivada: {}", t.getNombre());
        return TiendaDto.TiendaResponse.from(t);
    }
}

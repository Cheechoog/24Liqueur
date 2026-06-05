package com.licor.catalogoservice.service;

import com.licor.catalogoservice.dto.CatalogoDto;
import com.licor.catalogoservice.model.Categoria;
import com.licor.catalogoservice.model.Producto;
import com.licor.catalogoservice.repository.CategoriaRepository;
import com.licor.catalogoservice.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    // ── Categorías ────────────────────────────────────────────────────────────

    public List<CatalogoDto.CategoriaResponse> listarCategorias() {
        return categoriaRepository.findByActivaTrue().stream()
                .map(CatalogoDto.CategoriaResponse::from)
                .toList();
    }

    @Transactional
    public CatalogoDto.CategoriaResponse crearCategoria(CatalogoDto.CategoriaRequest req) {
        if (categoriaRepository.existsByNombre(req.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
        Categoria c = Categoria.builder()
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .activa(true)
                .build();
        return CatalogoDto.CategoriaResponse.from(categoriaRepository.save(c));
    }

    @Transactional
    public void desactivarCategoria(UUID id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
        c.setActiva(false);
        categoriaRepository.save(c);
    }

    @Transactional
    public CatalogoDto.CategoriaResponse activarCategoria(UUID id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));
        if (c.getActiva()) {
            throw new IllegalArgumentException("La categoría ya está activa.");
        }
        c.setActiva(true);
        categoriaRepository.save(c);
        log.info("Categoría reactivada: {}", c.getNombre());
        return CatalogoDto.CategoriaResponse.from(c);
    }

    // ── Productos ─────────────────────────────────────────────────────────────

    public List<CatalogoDto.ProductoResponse> listarProductos() {
        return productoRepository.findByActivoTrue().stream()
                .map(CatalogoDto.ProductoResponse::from)
                .toList();
    }

    public List<CatalogoDto.ProductoResponse> listarPorCategoria(UUID categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId).stream()
                .map(CatalogoDto.ProductoResponse::from)
                .toList();
    }

    public List<CatalogoDto.ProductoResponse> buscar(String query) {
        return productoRepository.buscar(query).stream()
                .map(CatalogoDto.ProductoResponse::from)
                .toList();
    }

    public CatalogoDto.ProductoResponse obtenerProducto(UUID id) {
        return productoRepository.findById(id)
                .map(CatalogoDto.ProductoResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
    }

    @Transactional
    public CatalogoDto.ProductoResponse crearProducto(CatalogoDto.ProductoRequest req) {
        Categoria categoria = categoriaRepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        Producto p = Producto.builder()
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .categoria(categoria)
                .marca(req.getMarca())
                .precio(req.getPrecio())
                .precioOferta(req.getPrecioOferta())
                .graduacion(req.getGraduacion())
                .volumenMl(req.getVolumenMl())
                .imagenUrl(req.getImagenUrl())
                .activo(true)
                .build();

        log.info("Producto creado: {}", p.getNombre());
        return CatalogoDto.ProductoResponse.from(productoRepository.save(p));
    }

    @Transactional
    public CatalogoDto.ProductoResponse actualizarProducto(UUID id, CatalogoDto.ProductoRequest req) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));

        Categoria categoria = categoriaRepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada."));

        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setCategoria(categoria);
        p.setMarca(req.getMarca());
        p.setPrecio(req.getPrecio());
        p.setPrecioOferta(req.getPrecioOferta());
        p.setGraduacion(req.getGraduacion());
        p.setVolumenMl(req.getVolumenMl());
        p.setImagenUrl(req.getImagenUrl());

        return CatalogoDto.ProductoResponse.from(productoRepository.save(p));
    }

    @Transactional
    public void desactivarProducto(UUID id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
        p.setActivo(false);
        productoRepository.save(p);
        log.info("Producto desactivado: {}", p.getNombre());
    }

    @Transactional
    public CatalogoDto.ProductoResponse activarProducto(UUID id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
        if (p.getActivo()) {
            throw new IllegalArgumentException("El producto ya está activo.");
        }
        p.setActivo(true);
        productoRepository.save(p);
        log.info("Producto reactivado: {}", p.getNombre());
        return CatalogoDto.ProductoResponse.from(p);
    }
}

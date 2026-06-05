package com.licor.catalogoservice.repository;

import com.licor.catalogoservice.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findByActivaTrue();
    boolean existsByNombre(String nombre);
}

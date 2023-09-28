package com.javaguides.springboot.repository;

import com.javaguides.springboot.model.BRAND;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/*
 * Clase BrandRepository usada realizar consultas en la base de datos a la tabla BRAND
 */
public interface BrandRepository extends JpaRepository<BRAND, Long> {
    Optional<BRAND> findById(long id);
}

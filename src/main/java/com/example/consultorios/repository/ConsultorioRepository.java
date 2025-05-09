package com.example.consultorios.repository;

import com.example.consultorios.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultorioRepository extends JpaRepository<Consultorio, Long> {
    Consultorio findByNombre(String nombre);
}

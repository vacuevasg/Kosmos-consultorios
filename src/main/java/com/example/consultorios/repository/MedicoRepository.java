package com.example.consultorios.repository;

import com.example.consultorios.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findByEspecialidad(String especialidad);
    List<Medico> findByConsultorioId(Long consultorioId);


}
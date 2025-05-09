package com.example.consultorios.repository;

import com.example.consultorios.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


    public interface CitaRepository extends JpaRepository<Cita, Long> {
        List<Cita> findByFecha(LocalDate fecha);
        List<Cita> findByMedicoId(Long medicoId);
        boolean existsByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora);
        boolean existsByConsultorioIdAndFechaAndHora(Long consultorioId, LocalDate fecha, LocalTime hora);

        List<Cita> findByPacienteAndFecha(String paciente, LocalDate fecha);

        long countByMedicoIdAndFecha(Long medicoId, LocalDate fecha);
        List<Cita> findByMedicoIdAndFechaAndHora(Long medicoId, LocalDate fecha, LocalTime hora);
        List<Cita> findByConsultorioIdAndFechaAndHora(Long consultorioId, LocalDate fecha, LocalTime hora);
        List<Cita> findByMedicoIdAndFecha(Long medicoId, LocalDate fecha);
    }


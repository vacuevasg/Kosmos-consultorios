package com.example.consultorios.service;

import com.example.consultorios.dto.CitaRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Cita;
import com.example.consultorios.model.Consultorio;
import com.example.consultorios.model.Medico;
import com.example.consultorios.repository.CitaRepository;
import com.example.consultorios.repository.ConsultorioRepository;
import com.example.consultorios.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;




    public List<Cita> getAll() {
        return citaRepository.findAll();
    }

    public Cita getById(Long id) throws BusinessException {
        Cita cita = citaRepository.findById(id).orElse(null);
        if(cita == null) {
            throw new BusinessException("Cita no encontrada con ID " + id, 404);
        }
        return cita;
    }

    public List<Cita> getByFecha(LocalDate fecha) {
        return citaRepository.findByFecha(fecha);
    }

    public Cita save(CitaRequest request) throws BusinessException {
        // Validar hora en punto
        if (request.getHora().getMinute() != 0 || request.getHora().getSecond() != 0) {
            throw new BusinessException("La hora de la cita debe estar en punto (ej. 9:00, 14:00).", 400);
        }

        // Verificar que el médico exista
        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new BusinessException("Médico no encontrado con ID " + request.getMedicoId(), 404));

        // Verificar que el consultorio exista
        Consultorio consultorio = consultorioRepository.findById(request.getConsultorioId())
                .orElseThrow(() -> new BusinessException("Consultorio no encontrado con ID " + request.getConsultorioId(), 404));

        // Verificar si el doctor ya tiene cita en esa fecha/hora
        boolean citaMedico = citaRepository.existsByMedicoIdAndFechaAndHora(
                request.getMedicoId(), request.getFecha(), request.getHora());

        if (citaMedico) {
            throw new BusinessException("El médico ya tiene una cita asignada en ese horario.", 409);
        }

        // Verificar si el consultorio ya tiene cita a esa hora
        boolean citaConsultorio = citaRepository.existsByConsultorioIdAndFechaAndHora(
                request.getConsultorioId(), request.getFecha(), request.getHora());
        if (citaConsultorio) {
            throw new BusinessException("El consultorio ya tiene una cita asignada en ese horario.", 409);
        }

        // Verificar que el paciente no tenga otra cita en el mismo horario ni con menos de 2 horas de diferencia ese día
        List<Cita> citasPaciente = citaRepository.findByPacienteAndFecha(request.getPaciente(), request.getFecha());

        for (Cita cita : citasPaciente) {
            long diferencia = Duration.between(cita.getHora(), request.getHora()).abs().toHours();
            if (diferencia < 3) {
                throw new BusinessException("El paciente ya tiene una cita cercana ese mismo día. Debe haber al menos 2 horas de diferencia.", 409);
            }
        }

        // Verificar que el médico no tenga más de 8 citas ese día
        long totalCitasMedico = citaRepository.countByMedicoIdAndFecha(request.getMedicoId(), request.getFecha());
        if (totalCitasMedico >= 8) {
            throw new BusinessException("El médico ya tiene el máximo de 8 citas asignadas para ese día.", 409);
        }

        // Crear la cita
        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMedico(medico);
        cita.setPaciente(request.getPaciente());
        cita.setConsultorio(consultorio);

        return citaRepository.save(cita);
    }
    public Cita update(CitaRequest request) throws BusinessException {


        if (request.getId() == null) {
            throw new BusinessException("ID de cita no puede ser nulo para actualizar.", 400);
        }

        // Validar hora en punto
        if (request.getHora().getMinute() != 0 || request.getHora().getSecond() != 0) {
            throw new BusinessException("La hora de la cita debe estar en punto (ej. 9:00, 14:00).", 400);
        }

        // Obtener cita original
        Cita citaOriginal = citaRepository.findById(request.getId())
                .orElseThrow(() -> new BusinessException("La cita a actualizar no fue encontrada.", 404));

        // Obtener médico y consultorio
        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new BusinessException("Médico no encontrado con ID " + request.getMedicoId(), 404));

        Consultorio consultorio = consultorioRepository.findById(request.getConsultorioId())
                .orElseThrow(() -> new BusinessException("Consultorio no encontrado con ID " + request.getConsultorioId(), 404));

        // Verificar que el médico no tenga cita en ese horario (ignorando la actual)
        boolean citaMedico = citaRepository.findByMedicoIdAndFechaAndHora(request.getMedicoId(), request.getFecha(), request.getHora()).stream()
                .anyMatch(c -> !c.getId().equals(request.getId()));

        if (citaMedico) {
            throw new BusinessException("El médico ya tiene una cita asignada en ese horario.", 409);
        }

        // Verificar si el consultorio tiene cita en ese horario (ignorando actual)
        boolean citaConsultorio = citaRepository.findByConsultorioIdAndFechaAndHora(request.getConsultorioId(), request.getFecha(), request.getHora()).stream()
                .anyMatch(c -> !c.getId().equals(request.getId()));

        if (citaConsultorio) {
            throw new BusinessException("El consultorio ya tiene una cita asignada en ese horario.", 409);
        }

        // Verificar citas del paciente ese día (excepto la actual)
        List<Cita> citasPaciente = citaRepository.findByPacienteAndFecha(request.getPaciente(), request.getFecha());
        for (Cita c : citasPaciente) {
            if (!c.getId().equals(request.getId())) {
                long diferencia = Duration.between(c.getHora(), request.getHora()).abs().toHours();
                if (diferencia < 3) {
                    throw new BusinessException("El paciente ya tiene una cita cercana ese mismo día. Debe haber al menos 2 horas de diferencia.", 409);
                }
            }
        }

        // Contar citas del médico ese día (excluyendo actual)
        long totalCitas = citaRepository.findByMedicoIdAndFecha(request.getMedicoId(), request.getFecha()).stream()
                .filter(c -> !c.getId().equals(request.getId()))
                .count();

        if (totalCitas >= 8) {
            throw new BusinessException("El médico ya tiene el máximo de 8 citas asignadas para ese día.", 409);
        }

        // Actualizar y guardar
        citaOriginal.setFecha(request.getFecha());
        citaOriginal.setHora(request.getHora());
        citaOriginal.setPaciente(request.getPaciente());
        citaOriginal.setMedico(medico);
        citaOriginal.setConsultorio(consultorio);

        return citaRepository.save(citaOriginal);
    }
    public List<Cita> buscarPorFiltros(LocalDate fecha, Long medicoId, Long consultorioId) {
        return citaRepository.findAll().stream()
                .filter(c -> fecha == null || c.getFecha().equals(fecha))
                .filter(c -> medicoId == null || c.getMedico().getId().equals(medicoId))
                .filter(c -> consultorioId == null || c.getConsultorio().getId().equals(consultorioId))
                .toList();
    }

    public void deleteById(Long id) {
        citaRepository.deleteById(id);
    }
}

package com.example.consultorios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaRequest {

    private Long id;

    @Schema(example = "2025-05-10", description = "Fecha de la cita (yyyy-MM-dd)")
    private LocalDate fecha;

    @Schema(example = "10:30", description = "Hora de la cita (HH:mm)")
    private LocalTime hora;

    @Schema(example = "1", description = "ID del médico que atenderá la cita")
    private Long medicoId;

    @Schema(example = "1", description = "ID del consultorio donde se atenderá la cita")
    private Long consultorioId;

    @Schema(example = "Alan", description = "Nombre del paciente")
    private String paciente;
}
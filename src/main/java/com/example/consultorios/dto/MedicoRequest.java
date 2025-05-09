package com.example.consultorios.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MedicoRequest {

    @Schema(example = "victor", description = "Nombre del médico")
    private String nombre;
    @Schema(example = "Cuevas", description = "Apellido paterno del médico")
    private String apellidoPaterno;
    @Schema(example = "Cuevas", description = "Apellido materno del médico")
    private String apellidoMaterno;
    @Schema(example = "Gastroenterologo", description = "Especialidad médica")
    private String especialidad;

}
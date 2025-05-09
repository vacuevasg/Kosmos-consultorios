package com.example.consultorios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConsultorioRequest {

    @Schema(example = "Consultorio Norte", description = "Nombre del consultorio")
    private String nombre;

    @Schema(example = "1", description = "Piso del consultorio")
    private String piso;


}
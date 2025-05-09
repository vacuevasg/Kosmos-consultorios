package com.example.consultorios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity

public @Data class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String piso;


    @OneToMany(mappedBy = "consultorio")
    private List<Medico> medicos;
    @OneToMany(mappedBy = "consultorio")
    private List<Cita> citas;
}
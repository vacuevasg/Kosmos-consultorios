package com.example.consultorios.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
public @Data class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String especialidad;


    @ManyToOne
    @JoinColumn(name = "consultorio_id")
    private Consultorio consultorio;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;


}

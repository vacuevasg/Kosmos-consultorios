package com.example.consultorios.config;

import com.example.consultorios.model.Consultorio;
import com.example.consultorios.model.Medico;
import com.example.consultorios.repository.ConsultorioRepository;
import com.example.consultorios.repository.MedicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ConsultorioRepository consultorioRepository, MedicoRepository medicoRepository) {
        return args -> {
            if (consultorioRepository.count() == 0) {

                    Consultorio c1 = new Consultorio();
                    c1.setNombre("Consultorio Reforma");
                    c1.setPiso("Piso 1");

                    Consultorio c2 = new Consultorio();
                    c2.setNombre("Consultorio Chapultepec");
                    c2.setPiso("Piso 2");

                    Consultorio c3 = new Consultorio();
                    c3.setNombre("Consultorio Insurgentes");
                    c3.setPiso("Piso 3");

                    Consultorio c4 = new Consultorio();
                    c4.setNombre("Consultorio Polanco");
                    c4.setPiso("Piso 4");

                    Consultorio c5 = new Consultorio();
                    c5.setNombre("Consultorio Roma Norte");
                    c5.setPiso("Piso 5");

                    consultorioRepository.save(c1);
                    consultorioRepository.save(c2);
                    consultorioRepository.save(c3);
                    consultorioRepository.save(c4);
                    consultorioRepository.save(c5);

            }

            if (medicoRepository.count() == 0) {

                    Medico m1 = new Medico();
                    m1.setNombre("María");
                    m1.setApellidoPaterno("González");
                    m1.setApellidoMaterno("Ramírez");
                    m1.setEspecialidad("Pediatría");


                    Medico m2 = new Medico();
                    m2.setNombre("Juan");
                    m2.setApellidoPaterno("Martínez");
                    m2.setApellidoMaterno("López");
                    m2.setEspecialidad("Cardiología");


                    Medico m3 = new Medico();
                    m3.setNombre("Ana");
                    m3.setApellidoPaterno("Hernández");
                    m3.setApellidoMaterno("Cruz");
                    m3.setEspecialidad("Dermatología");


                    Medico m4 = new Medico();
                    m4.setNombre("Carlos");
                    m4.setApellidoPaterno("Sánchez");
                    m4.setApellidoMaterno("Díaz");
                    m4.setEspecialidad("Medicina Interna");


                    Medico m5 = new Medico();
                    m5.setNombre("Laura");
                    m5.setApellidoPaterno("Morales");
                    m5.setApellidoMaterno("Torres");
                    m5.setEspecialidad("Ginecología");


                    medicoRepository.save(m1);
                    medicoRepository.save(m2);
                    medicoRepository.save(m3);
                    medicoRepository.save(m4);
                    medicoRepository.save(m5);

            }
        };
    }
}
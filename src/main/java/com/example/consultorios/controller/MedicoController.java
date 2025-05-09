package com.example.consultorios.controller;

import com.example.consultorios.dto.MedicoRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Medico;
import com.example.consultorios.service.MedicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    @Autowired
    private  MedicoService medicoService;
    private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);



    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Medico> lista = medicoService.getAll();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ERROR_GET_ALL_MEDICOS: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Medico medico = medicoService.getById(id);
            return new ResponseEntity<>(medico, HttpStatus.OK);

        } catch (BusinessException e) {
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.valueOf(e.getCode()));
        } catch (Exception e) {
            logger.error("ERROR_GET_MEDICO_BY_ID: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<?> getByEspecialidad(@PathVariable String especialidad) {
        try {
            return new ResponseEntity<>(medicoService.getByEspecialidad(especialidad), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ERROR_GET_MEDICO_BY_ESPECIALIDAD: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MedicoRequest medico) {
        try {
            return new ResponseEntity<>(medicoService.save(medico), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("ERROR_CREATE_MEDICO: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            medicoService.deleteById(id);
            return new ResponseEntity<>("{}", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("ERROR_DELETE_MEDICO: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
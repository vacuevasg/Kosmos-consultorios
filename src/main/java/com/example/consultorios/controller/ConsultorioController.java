package com.example.consultorios.controller;

import com.example.consultorios.dto.ConsultorioRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Consultorio;
import com.example.consultorios.service.ConsultorioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultorios")
@CrossOrigin(origins = "*")
public class ConsultorioController {

    @Autowired
    private ConsultorioService consultorioService;
    private static final Logger logger = LoggerFactory.getLogger(ConsultorioController.class);


    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(consultorioService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ERROR_GET_ALL_CONSULTORIOS: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            Consultorio consultorio = consultorioService.getById(id);
            return new ResponseEntity<>(consultorio, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.valueOf(e.getCode()));
        } catch (Exception e) {
            logger.error("ERROR_GET_CONSULTORIO_BY_ID: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre) {
        try {
            Consultorio consultorio = consultorioService.getByNombre(nombre);
            return new ResponseEntity<>(consultorio, HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.valueOf(e.getCode()));
        } catch (Exception e) {
            logger.error("ERROR_GET_CONSULTORIO_BY_NOMBRE: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ConsultorioRequest consultorio) {
        try {
            return new ResponseEntity<>(consultorioService.save(consultorio), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("ERROR_CREATE_CONSULTORIO: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            consultorioService.deleteById(id);
            return new ResponseEntity<>("{}", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("ERROR_DELETE_CONSULTORIO: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
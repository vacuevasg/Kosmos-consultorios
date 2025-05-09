package com.example.consultorios.controller;

import com.example.consultorios.dto.CitaRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Cita;
import com.example.consultorios.service.CitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private CitaService citaService;
    private static final Logger logger = LoggerFactory.getLogger(CitaController.class);



    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(citaService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("ERROR_GET_ALL_CITAS: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(citaService.getById(id), HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.valueOf(e.getCode()));
        } catch (Exception e) {
            logger.error("ERROR_GET_CITA_BY_ID: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CitaRequest cita) {
        try {
            return new ResponseEntity<>(citaService.save(cita), HttpStatus.CREATED);
        } catch (BusinessException e) {
            return new ResponseEntity<>("{\"error\":\"" + e.getMessage() + "\"}", HttpStatus.valueOf(e.getCode()));
        } catch (Exception e) {
            logger.error("ERROR_CREATE_CITA: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            citaService.deleteById(id);
            return new ResponseEntity<>("{}", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("ERROR_DELETE_CITA: " + e.getMessage());
            return new ResponseEntity<>("{\"error\":\"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
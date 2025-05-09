package com.example.consultorios.controller;

import com.example.consultorios.dto.CitaRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Cita;
import com.example.consultorios.service.CitaService;
import com.example.consultorios.service.ConsultorioService;
import com.example.consultorios.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private  CitaService citaService;
    @Autowired
    private  MedicoService medicoService;
    @Autowired
    private  ConsultorioService consultorioService;




    @GetMapping("/")
    public String home(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long medicoId,
            @RequestParam(required = false) Long consultorioId,
            Model model) {

        List<Cita> citas = citaService.buscarPorFiltros(fecha, medicoId, consultorioId);
        CitaRequest cita = new CitaRequest();
        cita.setHora(LocalTime.of(8, 0));
        cita.setFecha(LocalDate.now());
        model.addAttribute("cita", cita);
        model.addAttribute("fechaMinima", LocalDate.now());
        model.addAttribute("medicos", medicoService.getAll());
        model.addAttribute("consultorios", consultorioService.getAll());
        model.addAttribute("citas", citas);
        return "citas";
    }


    @PostMapping("/cita")
    public String crearCita(@ModelAttribute CitaRequest citaRequest, Model model) {
        try {
            if (citaRequest.getId() != null) {
                citaService.update(citaRequest);
            } else {
                citaService.save(citaRequest);
            }
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cita", citaRequest);
            model.addAttribute("medicos", medicoService.getAll());
            model.addAttribute("consultorios", consultorioService.getAll());
            model.addAttribute("citas", citaService.getAll());
            return "citas";
        }
        return "redirect:/";
    }


    @GetMapping("/cita/eliminar/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/cita/editar/{id}")
    public String editarCita(@PathVariable("id") Long id, Model model) {
        try{
        Cita cita = citaService.getById(id);
        CitaRequest citaRequest = new CitaRequest();

        citaRequest.setId(cita.getId());
        citaRequest.setPaciente(cita.getPaciente());
        citaRequest.setFecha(cita.getFecha());
        citaRequest.setHora(cita.getHora());
        citaRequest.setMedicoId(cita.getMedico().getId());
        citaRequest.setConsultorioId(cita.getConsultorio().getId());

        model.addAttribute("cita", citaRequest);
        model.addAttribute("medicos", medicoService.getAll());
        model.addAttribute("consultorios", consultorioService.getAll());
        model.addAttribute("citas", citaService.getAll());
        return "citas";
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cita", new CitaRequest());
            model.addAttribute("medicos", medicoService.getAll());
            model.addAttribute("consultorios", consultorioService.getAll());
            model.addAttribute("citas", citaService.getAll());
            return "citas";
        }
    }
}
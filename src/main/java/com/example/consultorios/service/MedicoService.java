package com.example.consultorios.service;

import com.example.consultorios.dto.MedicoRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Consultorio;
import com.example.consultorios.model.Medico;
import com.example.consultorios.repository.ConsultorioRepository;
import com.example.consultorios.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private  MedicoRepository medicoRepository;
    @Autowired
    private ConsultorioRepository consultorioRepository;




    public List<Medico> getAll() {
        return medicoRepository.findAll();
    }

    public Medico getById(Long id) throws BusinessException {
       Medico medico = medicoRepository.findById(id).orElse(null);
       if(medico == null){
           throw new BusinessException("El Medico solicitado no existe.",400);
       }
       return medico;
    }

    public List<Medico> getByEspecialidad(String especialidad) {
        return medicoRepository.findByEspecialidad(especialidad);
    }

    public Medico save(MedicoRequest request) throws BusinessException {



        Medico medico = new Medico();
        medico.setNombre(request.getNombre());
        medico.setEspecialidad(request.getEspecialidad());
        medico.setApellidoPaterno(request.getApellidoPaterno());
        medico.setApellidoMaterno(request.getApellidoMaterno());


        return medicoRepository.save(medico);
    }

    public void deleteById(Long id) {
        medicoRepository.deleteById(id);
    }
}

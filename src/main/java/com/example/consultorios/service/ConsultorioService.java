package com.example.consultorios.service;

import com.example.consultorios.dto.ConsultorioRequest;
import com.example.consultorios.exception.BusinessException;
import com.example.consultorios.model.Consultorio;
import com.example.consultorios.repository.ConsultorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private ConsultorioRepository consultorioRepository;



    public List<Consultorio> getAll() {
        return consultorioRepository.findAll();
    }

    public Consultorio getById(Long id) throws BusinessException {
        Consultorio consultorio =  consultorioRepository.findById(id).orElse(null);
        if(consultorio == null){
            throw new BusinessException("No se puedo obtener el consulto por el Id "+ id,404);
        }
        return consultorio;
    }

    public Consultorio save(ConsultorioRequest request) {
        Consultorio consultorio = new Consultorio();
        consultorio.setNombre(request.getNombre());
        consultorio.setPiso(request.getPiso());


        return consultorioRepository.save(consultorio);
    }

    public void deleteById(Long id) {
        consultorioRepository.deleteById(id);
    }

    public Consultorio getByNombre(String nombre) throws BusinessException {
         Consultorio consultorio = consultorioRepository.findByNombre(nombre);
         if(consultorio == null){
             throw new BusinessException("No hay consultorios con ese nombre.",404);
         }
         return consultorio;
    }
}
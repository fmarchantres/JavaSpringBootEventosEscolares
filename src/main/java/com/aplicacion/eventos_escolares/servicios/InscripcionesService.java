package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Inscripciones;
import com.aplicacion.eventos_escolares.repositories.InscripcionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionesService {
    @Autowired
    private InscripcionesRepository inscripcionesRepository;

    public List<Inscripciones> listarTodas(){
        return inscripcionesRepository.findAll();
    }

    public Optional<Inscripciones> buscarPorId(Integer id){
        return inscripcionesRepository.findById(id);
    }

    public Inscripciones guardar(Inscripciones inscripcion){
        return inscripcionesRepository.save(inscripcion);
    }

    public void eliminar(Integer id){
        inscripcionesRepository.deleteById(id);
    }
}

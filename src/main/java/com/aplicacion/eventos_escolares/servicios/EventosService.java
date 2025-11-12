package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Eventos;
import com.aplicacion.eventos_escolares.repositories.EventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;

    public List<Eventos> listarTodos(){
        return eventosRepository.findAll();
    }

    public Optional<Eventos> buscarPorId(Integer id){
        return eventosRepository.findById(id);
    }

    public Eventos guardar(Eventos evento){
        return eventosRepository.save(evento);
    }

    public void eliminar(Integer id){
        eventosRepository.deleteById(id);
    }

    public Optional<Eventos> buscarPorNombre(String nombre){
        return Optional.ofNullable(eventosRepository.findByNombre(nombre));
    }
}

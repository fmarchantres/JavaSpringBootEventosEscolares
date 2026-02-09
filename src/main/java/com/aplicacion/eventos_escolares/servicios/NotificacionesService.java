package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Notificaciones;
import com.aplicacion.eventos_escolares.repositories.NotificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionesService {
    //Esto es solo un comentario para comprobar github
    @Autowired
    private NotificacionesRepository notificacionesRepository;

    public List<Notificaciones> listarTodas(){
        return notificacionesRepository.findAll();
    }

    public Optional<Notificaciones> buscarPorId(Integer id){
        return notificacionesRepository.findById(id);
    }

    public Notificaciones guardar(Notificaciones notificacion){
        return notificacionesRepository.save(notificacion);
    }

    public void eliminar(Integer id){
        notificacionesRepository.deleteById(id);
    }
}

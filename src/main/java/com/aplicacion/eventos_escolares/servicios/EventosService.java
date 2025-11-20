package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDetallesDTO;
import com.aplicacion.eventos_escolares.modelos.Eventos;
import com.aplicacion.eventos_escolares.modelos.Usuarios;
import com.aplicacion.eventos_escolares.repositories.EventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventosService {
    @Autowired
    private EventosRepository eventosRepository;
    @Autowired
    private UsuariosService usuariosService;

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

    public Eventos guardarDesdeDTO(CrearEventoDTO dto){
        Eventos evento = new Eventos();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setLugar(dto.getLugar());
        evento.setRequisitos(dto.getRequisitos());
        evento.setPrecio(dto.getPrecio());

        //CONVERTIR FECHA DE STRING a LOCALDATE
        LocalDateTime fecha = LocalDateTime.parse(dto.getFecha());
        evento.setFecha(fecha);

        //BUSCAR EL USUARIO CREADOR POR ID
        Usuarios creador = usuariosService.buscarPorId(dto.getCreadorId()).orElse(null);
        evento.setCreador(creador);
        return eventosRepository.save(evento);
    }

    public EventoDetallesDTO convertirAEventoDetallesDTO(Eventos evento){
        EventoDetallesDTO dto = new EventoDetallesDTO();
        dto.setId(evento.getId());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setLugar(evento.getLugar());
        dto.setFecha(evento.getFecha());
        dto.setRequisitos(evento.getRequisitos());
        dto.setPrecio(evento.getPrecio());
        //SOLO MANDAMOS EL ID DEL CREADOR
        dto.setCreadorId(evento.getCreador().getId());
        return dto;
    }


}

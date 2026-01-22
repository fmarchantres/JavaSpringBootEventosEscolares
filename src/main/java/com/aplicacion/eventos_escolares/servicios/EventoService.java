package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.CrearEventoMapper;
import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.ModificarEventoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    //Si no son final lombok no genera el constructor
    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;
    private final UsuarioService usuarioService;
    private final CrearEventoMapper crearEventoMapper;
    private final UsuarioRepository usuarioRepository;


    /*----------------------------------------------------------------*/
    //METODO PARA OBTENER EVENTO POR ID
    /*----------------------------------------------------------------*/


    public EventoDTO obtenerPorId(Integer id){
        Evento eventoId = eventoRepository.findById(id).orElse(null);

        if (eventoId == null){
            throw new ElementoNoEncontradoException("Evento no encontrado");
        }


        return eventoMapper.toDTO(eventoId);
    }


    /*----------------------------------------------------------------*/
    //METODO PARA OBTENER EVENTO SEGUN FILTRO
    /*----------------------------------------------------------------*/

    public List<EventoDTO> obtenerConFiltros (String lugar, LocalDate fecha){

        List<Evento> eventos;

        //PRIMER CASO, MUESTRA TODOS
        if (lugar == null && fecha == null){
            return eventoMapper.toDTOList(eventoRepository.findAll());
        }


        //SI HAY FECHA, SE CONVIERTE A LOCALDATE
        LocalDateTime inicioDia = null;
        LocalDateTime finDia = null;

        if (fecha != null){
            inicioDia = fecha.atStartOfDay(); //00:00
            finDia = fecha.atTime (23,59,59); //23:59
        }



        //FILTRO COMBINADO (lugar y fecha)
        if (lugar != null && fecha != null) {
            eventos = eventoRepository.findByLugarContainingIgnoreCaseAndFechaBetween(lugar, inicioDia, finDia);
            return eventoMapper.toDTOList(eventos);
        }
        //SOLO MUESTRA POR LUGAR
        else if (lugar != null){
            eventos = eventoRepository.findByLugarContainingIgnoreCase(lugar);

        }
        //SOLO FILTRA POR FECHA
        else {
            eventos = eventoRepository.findByFechaBetween(inicioDia, finDia);
            return eventoMapper.toDTOList(eventos);
        }

        if (eventos.isEmpty()){
            throw new ElementoNoEncontradoException("Evento con filtro no encontrado");
        }


        return eventoMapper.toDTOList(eventos);
    }


    /*----------------------------------------------------------------*/
    //CREAR EVENTO
    /*----------------------------------------------------------------*/
    public EventoDTO crearEvento(CrearEventoDTO dto){
        // 1.Convertimos DTO → Entity
        Evento evento = crearEventoMapper.toEntity(dto);

        // 2.Buscamos el usuario creador
        Usuario creador = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado"));

        // 3.Asignamos creador al evento
        evento.setCreador(creador);

        // 4.Guardamos el evento en la BD
        Evento eventoCreado = eventoRepository.save(evento);

        // 5.Devolvemos un EventoDTO para el front
        return crearEventoMapper.toDTO(eventoCreado);
    }


    /*----------------------------------------------------------------*/
    //MODIFICAR EVENTO
    /*----------------------------------------------------------------*/
    public EventoDTO modificarEvento (Integer id, ModificarEventoDTO dto){

        //Buscar evento
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ElementoNoEncontradoException ("Evento no encontrado"));

        //Modificar solo los campos permitidos (al final todos)
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());
        evento.setRequisitos(dto.getRequisitos());
        evento.setPrecio(dto.getPrecio());
        evento.setUrlImagen(dto.getUrlImagen());

        //Guardar
        Evento eventoActualizado = eventoRepository.save(evento);

        //Devolver DTO
        return eventoMapper.toDTO(eventoActualizado);

    }

    //ELIMINAR EVENTO
    public void eliminarEvento (Integer id) {
        if (!eventoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento no encontrado");
        }
        eventoRepository.deleteById(id);
    }


    public List<EventoDTO> mostrarTodos() {
        return eventoMapper.toDTOList(eventoRepository.findAll());
    }




    //EVENTOS DESTACADOS

    public List <EventoDTO> obtenerEventosDestacados(){
        List<Integer> idsDestacados = List.of(4,3);
        List<Evento> eventos = eventoRepository.findByIdIn(idsDestacados);

        if (eventos.isEmpty()) {
            throw new ElementoNoEncontradoException("No hay eventos destacados");
        }
        return eventoMapper.toDTOList(eventos);

    }
}



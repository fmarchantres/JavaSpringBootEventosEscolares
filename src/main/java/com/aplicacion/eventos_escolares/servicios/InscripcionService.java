package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.InscripcionMapper;
import com.aplicacion.eventos_escolares.dto.EstadisticasDTO;
import com.aplicacion.eventos_escolares.dto.InscripcionDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioParticipaEventoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Foto;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private InscripcionMapper inscripcionMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    /*----------------------------------------------------------------*/
    //REGISTRAR USUARIO
    /*----------------------------------------------------------------*/

    public Inscripcion registrarUsuario(Integer eventoId, InscripcionDTO dto) {

        //Primero verificamos que el usuario NO esté ya inscrito
        if(inscripcionRepository.existsByUsuarioIdAndEventoId(dto.getUsuarioId(), eventoId)) {
            throw new ElementoNoEncontradoException("El usuario ya está inscrito en este evento");
        }


        //Buscar evento
        Evento evento = eventoService.buscarPorId(eventoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Evento no encontrado"));


        //Busca usuario
        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId())
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado"));


        //Convertir DTO a Entity
        Inscripcion inscripcion = inscripcionMapper.toEntity(dto);

        //Asignar usuario y evento a la inscripcion
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        //Guardamos y devolvemos la entidad COMPLETA
        return inscripcionGuardada;
    }




    public List<Inscripcion> obtenerInscripcionesUsuario (Integer usuarioId) {

        return inscripcionRepository.findByUsuarioId(usuarioId);
    }

    public List<EstadisticasDTO>  obtenerEstadisticasEventos() {
        return inscripcionRepository.estadisticas();
    }


    public List <UsuarioParticipaEventoDTO> obtenerEventosPorId (Integer usuarioId) {

        //Obtenemos inscripciones del usuario
        List<Inscripcion> listaInscripciones = inscripcionRepository.findByUsuarioId(usuarioId);

        //Creamos lista de DTOs
        List<UsuarioParticipaEventoDTO> listaDTO = new ArrayList<>();
        //La recorremos
        for (Inscripcion inscripcion : listaInscripciones) {

            UsuarioParticipaEventoDTO dto = new UsuarioParticipaEventoDTO();

            dto.setUsuarioId(inscripcion.getUsuario().getId());
            dto.setEventoId(inscripcion.getEvento().getId());
            dto.setEventoNombre(inscripcion.getEvento().getNombre());
            dto.setUsuarioNombre(inscripcion.getUsuario().getNombre());
            listaDTO.add(dto);
        }

        return listaDTO;
    }



    //CRUD
    public List<Inscripcion> listarTodas() {
        return inscripcionRepository.findAll();
    }
    public Optional<Inscripcion> buscarPorId(Integer id) {
        return inscripcionRepository.findById(id);
    }
    public List<Inscripcion> buscarPorUsuario(Integer id) {
        //primero validamos que el usuario existe
        usuarioService.buscarPorId(id).orElseThrow(() -> new ElementoNoEncontradoException("Usuario insertado no encontrado"));
        return inscripcionRepository.findByUsuarioId(id);
    }

    public Inscripcion guardar(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }
    public void eliminar(Integer id) {
        inscripcionRepository.deleteById(id);
    }







}



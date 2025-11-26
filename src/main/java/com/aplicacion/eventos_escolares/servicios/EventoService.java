package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.CrearEventoMapper;
import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.ModificarEventoDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

/*
    //METODO PARA LISTAR TODOS LOS EVENTOS EN DTO
    public List<EventoDTO> obtenerTodos(){
        List<Evento> eventos = eventoRepository.findAll(); //Obtiene todos
        return eventoMapper.toDTOList(eventos); //Convierte a DTO
    }


 */
    /*----------------------------------------------------------------*/

    //METODO PARA OBTENER EVENTO POR ID
    public EventoDTO obtenerPorId(Integer id){
        Evento eventoId = eventoRepository.findById(id).orElse(null);

        if (eventoId == null) return null;
        return eventoMapper.toDTO(eventoId);
    }

    /*----------------------------------------------------------------*/

    //METODO PARA OBTENER EVENTO SEGUN FILTRO
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

        if (lugar != null && fecha != null){
            eventos = eventoRepository.findByLugarContainingIgnoreCaseAndFechaBetween(lugar, inicioDia, finDia);
            return eventoMapper.toDTOList(eventos);
        }

        //SOLO MUESTRA POR LUGAR
        if (lugar != null){
            eventos = eventoRepository.findByLugarContainingIgnoreCase(lugar);
            return eventoMapper.toDTOList(eventos);
        }

        //SOLO FILTRA POR FECHA
        if (fecha != null){
            eventos = eventoRepository.findByFechaBetween(inicioDia, finDia);
            return eventoMapper.toDTOList(eventos);
        }


        return eventoMapper.toDTOList(eventoRepository.findAll());
    }

    /*----------------------------------------------------------------*/

    //CREAR EVENTO

    public EventoDTO crearEvento(CrearEventoDTO dto){
        // 1. Convertimos DTO → Entity
        Evento evento = crearEventoMapper.toEntity(dto);

        // 2. Buscamos el usuario creador
        Usuario creador = usuarioService.buscarPorId(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Asignamos creador al evento
        evento.setCreador(creador);

        // 4. Guardamos el evento en la BD
        Evento eventoCreado = eventoRepository.save(evento);

        // 5. Devolvemos un EventoDTO para el front
        return crearEventoMapper.toDTO(eventoCreado);
    }
    /*----------------------------------------------------------------*/


    /*----------------------------------------------------------------*/
    //MODIFICAR EVENTO

    public EventoDTO modificarEvento (Integer id, ModificarEventoDTO dto){

        // 1. Buscar evento
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // 2. Modificar solo los campos permitidos
        evento.setDescripcion(dto.getDescripcion());
        evento.setFecha(dto.getFecha());
        evento.setLugar(dto.getLugar());

        // 3. Guardar
        Evento eventoActualizado = eventoRepository.save(evento);

        // 4. Devolver DTO
        return eventoMapper.toDTO(eventoActualizado);

    }

    public Optional<Evento> buscarPorId(Integer id){
        return eventoRepository.findById(id);
    }





    /*----------------------------------------------------------------*/



}















    /*
    @Autowired
    private UsuariosService usuariosService;

    private final EventoMapper eventoMapper;

    public List<Evento> listarTodos(){
        return eventosRepository.findAll();
    }



    public Evento guardar(Evento evento){
        return eventosRepository.save(evento);
    }

    public void eliminar(Integer id){
        eventosRepository.deleteById(id);
    }

    public Optional<Evento> buscarPorNombre(String nombre){
        return Optional.ofNullable(eventosRepository.findByNombre(nombre));
    }

    public Evento guardarDesdeDTO(EventoDTO dto){
//        Eventos evento = new Eventos();
//        evento.setNombre(dto.getNombre());
//        evento.setDescripcion(dto.getDescripcion());
//        evento.setLugar(dto.getLugar());
//        evento.setRequisitos(dto.getRequisitos());
//        evento.setPrecio(dto.getPrecio());
//
//        //CONVERTIR FECHA DE STRING a LOCALDATE
//        LocalDateTime fecha = LocalDateTime.parse(dto.getFecha());
//        evento.setFecha(fecha);
//
//        //BUSCAR EL USUARIO CREADOR POR ID
//        Usuarios creador = usuariosService.buscarPorId(dto.getCreadorId()).orElse(null);
//        evento.setCreador(creador);
        Evento evento = eventoMapper.toEntity(dto);
        return eventosRepository.save(evento);
    }

    /*public EventoDTO convertirAEventoDetallesDTO(Eventos evento){
        EventoDTO dto = new EventoDTO();
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


    @Transactional
    public List<EventoDTO> findAll(){
        List<Evento> eventos = eventosRepository.findAll();
        return eventoMapper.toDTOList(eventos);

    }

    */



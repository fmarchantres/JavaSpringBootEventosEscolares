package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    //Si no son final lombok no genera el constructor
    private final EventoRepository eventoRepository;
    private final EventoMapper eventoMapper;


    //METODO PARA LISTAR TODOS LOS EVENTOS EN DTO
    public List<EventoDTO> obtenerTodos(){
        List<Evento> eventos = eventoRepository.findAll(); //Obtiene todos
        return eventoMapper.toDTOList(eventos); //Convierte a DTO
    }

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



}















    /*
    @Autowired
    private UsuariosService usuariosService;

    private final EventoMapper eventoMapper;

    public List<Evento> listarTodos(){
        return eventosRepository.findAll();
    }

    public Optional<Evento> buscarPorId(Integer id){
        return eventosRepository.findById(id);
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



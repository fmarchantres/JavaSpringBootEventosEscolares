package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.CrearEventoMapper;
import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.dto.*;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import com.aplicacion.eventos_escolares.repositories.InscripcionRepository;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceMockitoTest {


    @InjectMocks
    private EventoService eventoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private CrearEventoMapper crearEventoMapper;

    @Mock
    private EventoMapper eventoMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private InscripcionRepository inscripcionRepository;


    /*----------------------------------------------------------------*/
    //TEST - 2. CREAR EVENTO
    /*----------------------------------------------------------------*/
    /*
    @Test
    void crearEventoCorrectamente() {

        CrearEventoDTO dto = new CrearEventoDTO();;
        dto.setUsuarioId(1);

        Usuario usuario = new Usuario();
        Evento evento = new Evento();
        Evento eventoGuardado = new Evento();
        EventoDTO eventoDTO = new EventoDTO();


        when(crearEventoMapper.toEntity(dto))
                .thenReturn(evento);

        when (usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        when (eventoRepository.save(evento))
                .thenReturn(eventoGuardado);

        when (crearEventoMapper.toDTO(eventoGuardado))
                .thenReturn(eventoDTO);

        this.eventoService.crearEvento(dto);

        Mockito.verify(crearEventoMapper, Mockito.times(1)).toEntity(dto);
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(1);
        Mockito.verify(eventoRepository, Mockito.times(1)).save(evento);
        Mockito.verify(crearEventoMapper, Mockito.times(1)).toDTO(eventoGuardado);
    }

    */


    /*----------------------------------------------------------------*/
    //TEST - 2. CREAR EVENTO NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void crearEventoNegativo() {

        // DTO que llega desde el controlador
        CrearEventoDTO dto = new CrearEventoDTO();
        dto.setUsuarioId(99); // un ID que NO existe

        // Simulamos que el servicio de usuario NO encuentra al creador
        when(crearEventoMapper.toEntity(dto)).thenReturn(new Evento());

        when(usuarioRepository.findById(99))
                .thenReturn(Optional.empty());


        // ejecutamos y comprobamos la excepción


        ElementoNoEncontradoException ex = assertThrows(
                ElementoNoEncontradoException.class,
                () -> eventoService.crearEvento(dto)
        );

        // Comprobamos que el mensaje es el esperado
        assertEquals("Usuario no encontrado", ex.getMessage());


        verify(usuarioRepository, Mockito.times(1)).findById(99);
        verify(eventoRepository,never()).save(any(Evento.class));
    }




    /*----------------------------------------------------------------*/
    //TEST - 3. FILTRAR EVENTO NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void filtrarEventosNegativo() {

        // Simulamos que el repositorio NO encuentra ningún evento
        // cuando se busca por "Biblioteca".
        // Devuelve una lista vacía.
        when(eventoRepository.findByLugarContainingIgnoreCase("Biblioteca"))
                .thenReturn(List.of());


        // ejecutamos y comprobamos la excepción
        ElementoNoEncontradoException ex = assertThrows(
                ElementoNoEncontradoException.class,
                () -> eventoService.obtenerConFiltros("Biblioteca", null)
        );

        // Comprobamos que el mensaje es el esperado
        assertEquals("Evento con filtro no encontrado", ex.getMessage());
    }


    /*----------------------------------------------------------------*/
    //TEST - 4. OBTENER DETALLES DE EVENTO
    /*----------------------------------------------------------------*/

    @Test
    void obtenerEventoPorIdNegativo() {

        //Simulamos que el repositorio no encuentra el evento, por lo que devuelve optional.empty
        when(eventoRepository.findById(178)).thenReturn(Optional.empty());

        //llamamos al metodo y esperamos la excepcion
        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> eventoService.obtenerPorId(178));

        //Comprobamos que el mensaje es el esperado
        assertEquals("Evento no encontrado", exception.getMessage());

        verify(eventoRepository, Mockito.times(1)).findById(178);

    }


    /*----------------------------------------------------------------*/
    //TEST - 5 POSITIVO
    /*----------------------------------------------------------------*/
    /*
    @Test
    void modificarEvento(){

        when(eventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Evento()));

        when(eventoRepository.save(Mockito.mock(Evento.class)))
                .thenReturn(new Evento());

        when(eventoMapper.toDTO(Mockito.mock(Evento.class)))
                .thenReturn(new EventoDTO());


        this.eventoService.modificarEvento(1, Mockito.mock(ModificarEventoDTO.class));

        Mockito.verify(eventoRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Mockito.verify(eventoRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(eventoMapper, Mockito.times(1)).toDTO(Mockito.any());
    }

     */

    /*----------------------------------------------------------------*/
    //TEST - 5. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void modificarEventoNegativo(){

        when(eventoRepository.findById(-1))
                .thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class,
                () -> eventoService.modificarEvento(-1, Mockito.mock(ModificarEventoDTO.class)));

        verify(eventoRepository, Mockito.times(1)).findById(-1);

    }












}

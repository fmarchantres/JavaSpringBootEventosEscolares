package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.CrearEventoMapper;
import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
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


    /*----------------------------------------------------------------*/
    //TEST - 2. CREAR EVENTO
    /*----------------------------------------------------------------*/
    @Test
    void crearEventoCorrectamente() {
        Usuario creador = new Usuario();
        creador.setId(1);

        CrearEventoDTO dto = new CrearEventoDTO();
        dto.setUsuarioId(1);

        Evento eventoSinGuardar = new Evento();
        Evento eventoGuardado = new Evento();
        eventoGuardado.setId(10);
        eventoGuardado.setCreador(creador);

        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(10);
        eventoDTO.setUsuarioId(1);

        when(crearEventoMapper.toEntity(dto)).thenReturn(eventoSinGuardar);
        when(usuarioService.buscarPorId(1)).thenReturn(Optional.of(creador));
        when(eventoRepository.save(any())).thenReturn(eventoGuardado);
        when(crearEventoMapper.toDTO(eventoGuardado)).thenReturn(eventoDTO);

        EventoDTO resultado = eventoService.crearEvento(dto);

        assertEquals(10, resultado.getId());
    }


    /*----------------------------------------------------------------*/
    //TEST - 2. CREAR EVENTO NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void crearEventoNegativo() {

        // DTO que llega desde el controlador
        CrearEventoDTO dto = new CrearEventoDTO();
        dto.setUsuarioId(99); // un ID que NO existe

        // Simulamos que el servicio de usuario NO encuentra al creador
        when(usuarioService.buscarPorId(99))
                .thenReturn(Optional.empty());


        // ejecutamos y comprobamos la excepción


        ElementoNoEncontradoException ex = assertThrows(
                ElementoNoEncontradoException.class,
                () -> eventoService.crearEvento(dto)
        );

        // Comprobamos que el mensaje es el esperado
        assertEquals("Usuario no encontrado", ex.getMessage());
    }


    /*----------------------------------------------------------------*/
    //TEST - 3. FILTRAR EVENTO
    /*----------------------------------------------------------------*/
    @Test
    void filtrarEventos() {
        //CREAMOS DOS EVENTOS SIMULADOS
        Evento eventoBiblioteca = new Evento();
        eventoBiblioteca.setId(1);
        eventoBiblioteca.setLugar("Biblioteca");

        Evento eventoGimnasio = new Evento();
        eventoGimnasio.setId(2);
        eventoGimnasio.setLugar("Gimnasio");

        //Lista que devolverá el repositorio cuando se busque por biblioteca
        List<Evento> listaFiltrada = List.of(eventoBiblioteca);

        //Simulamos el comportamiento del repositorio
        when(eventoRepository.findByLugarContainingIgnoreCase("Biblioteca")).thenReturn(listaFiltrada);

        //Preparamos el DTO que devolverá el mapper
        EventoDTO dtoBiblioteca = new EventoDTO();
        dtoBiblioteca.setId(1);
        dtoBiblioteca.setLugar("Biblioteca");

        //Simulamos el comportamiento del mapper
        when(eventoMapper.toDTOList(listaFiltrada)).thenReturn(List.of(dtoBiblioteca));

        //WHEN
        //Ejecutamos el metodo a probar
        List<EventoDTO> resultado = eventoService.obtenerConFiltros("Biblioteca", null);


        //Comprobamos que el resultado es el esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Biblioteca", resultado.get(0).getLugar());

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

    }



}

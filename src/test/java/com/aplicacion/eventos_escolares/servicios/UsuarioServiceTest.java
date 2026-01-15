package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;


    /*----------------------------------------------------------------*/
    //TEST - 1. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void registrarUsuario_Positive() {

        //GIVEN (Lo que se dá para hacer el test, datos, inicializaciones)
        //PREVIOS
        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("prueba@test.com");
        dto.setNombre("Fran");
        dto.setPassword("1234");
        dto.setPrimerApellido("López");
        dto.setSegundoApellido("Gómez");


        //THEN (Lo que pasa en el test, cuando llamo, que pruebo)
        //EJECUCION
        Usuario usuario = usuarioService.registrarUsuario(dto);

        //WHEN (Cuando he llamado al test que compruebo)
        //COMPROBACIONES
        assertNotNull(usuario.getId());
        assertEquals("prueba@test.com", usuario.getEmail());
        assertNotNull(usuario.getFechaRegistro());
    }


    /*----------------------------------------------------------------*/
    //TEST - 1. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void registrarUsuario_Negative() {
        RegistrarUsuarioDTO dto1 = new RegistrarUsuarioDTO();
        dto1.setEmail("prueba@test.com");
        dto1.setNombre("Fran");
        dto1.setPassword("1234");
        dto1.setPrimerApellido("Lopez");
        dto1.setSegundoApellido("Gomez");

        usuarioService.registrarUsuario(dto1);

        RegistrarUsuarioDTO dto2 = new RegistrarUsuarioDTO();
        dto2.setEmail("prueba@test.com"); //duplicado
        dto2.setNombre("Otro");
        dto2.setPassword("1234");
        dto2.setPrimerApellido("Perez");
        dto2.setSegundoApellido("Diaz");


        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {usuarioService.registrarUsuario(dto2);});
        assertEquals(exception.getMessage(), "El email ya está registrado");
    }


    /*----------------------------------------------------------------*/
    //TEST - 2. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void crearEventoPositive() {

        //GIVEN (Lo que se dá para hacer el test, datos, inicializaciones)
        //PREVIOS
        //PRIMERO CREAMOS EL USUARIO CREADOR DEL EVENTO
        Usuario creador = new Usuario();
        creador.setEmail("creador@test.com");
        creador.setNombre("Pedro");
        creador.setPassword("1234");
        creador.setPrimerApellido("Lopez");
        creador = usuarioService.guardar(creador);

        CrearEventoDTO eventoDto = new CrearEventoDTO();

        eventoDto.setNombre("Torneo de Ajedrez");
        eventoDto.setDescripcion("Torneo para todos los alumnos");
        eventoDto.setFecha(LocalDateTime.now());
        eventoDto.setLugar("Biblioteca");
        eventoDto.setRequisitos("Inscripción necesaria");
        eventoDto.setPrecio(5.0);
        eventoDto.setUrlImagen("https://imagen.jpg");
        eventoDto.setUsuarioId(creador.getId());




        //THEN (Lo que pasa en el test, cuando llamo, que pruebo)
        //EJECUCION

        EventoDTO evento = eventoService.crearEvento(eventoDto);


        //WHEN (Cuando he llamado al test que compruebo)
        //COMPROBACIONES
        assertNotNull(evento.getId());
        assertEquals("Torneo de Ajedrez", evento.getNombre());
        assertEquals("Biblioteca", evento.getLugar());
        assertEquals(creador.getId(), evento.getUsuarioId());
    }


    /*----------------------------------------------------------------*/
    //TEST - 2. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void crearEventoNegative() {
        CrearEventoDTO eventoDto = new CrearEventoDTO();

        eventoDto.setNombre("Torneo de Ajedrez");
        eventoDto.setDescripcion("Torneo para todos los alumnos");
        eventoDto.setFecha(LocalDateTime.now());
        eventoDto.setLugar("Biblioteca");
        eventoDto.setRequisitos("Inscripción necesaria");
        eventoDto.setPrecio(5.0);
        eventoDto.setUrlImagen("https://imagen.jpg");
        eventoDto.setUsuarioId(123); //no existe

        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {eventoService.crearEvento(eventoDto);});
        assertEquals("Usuario no encontrado", exception.getMessage());
    }


    /*----------------------------------------------------------------*/
    //TEST - 3. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void filtrarEventos() {

        //GIVEN
        Usuario creador = new Usuario();
        creador.setEmail("creador@test.com");
        creador.setNombre("Pedro");
        creador.setPassword("1234");
        creador.setPrimerApellido("Lopez");
        creador = usuarioService.guardar(creador);

        //EVENTOS CREADOS
        CrearEventoDTO evento1 = new CrearEventoDTO();

        evento1.setNombre("Torneo de Ajedrez");
        evento1.setDescripcion("Torneo para todos los alumnos");
        evento1.setFecha(LocalDateTime.now());
        evento1.setLugar("Biblioteca");
        evento1.setRequisitos("Inscripción obligatoria");
        evento1.setPrecio(5.0);
        evento1.setUrlImagen("https://imagen.jpg");
        evento1.setUsuarioId(creador.getId());


        CrearEventoDTO evento2 = new CrearEventoDTO();

        evento2.setNombre("Torneo de Balonmano");
        evento2.setDescripcion("Torneo para los alumnos de Educación Física");
        evento2.setFecha(LocalDateTime.now());
        evento2.setLugar("Gimnasio");
        evento2.setRequisitos("");
        evento2.setPrecio(5.0);
        evento2.setUrlImagen("https://imagen12.jpg");
        evento2.setUsuarioId(creador.getId());


        //GUARDAR EVENTOS
        eventoService.crearEvento(evento1);
        eventoService.crearEvento(evento2);

        //THEN
        List<EventoDTO> resultado = eventoService.obtenerConFiltros("Biblioteca", null);

        //WHEN
        assertEquals(1, resultado.size());
        assertEquals("Biblioteca",  resultado.get(0).getLugar());

    }


    /*----------------------------------------------------------------*/
    //TEST - 3. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void filtrarEventosNegativo() {}


}







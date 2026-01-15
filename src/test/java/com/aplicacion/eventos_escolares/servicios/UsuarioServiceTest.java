package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.*;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
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
    @Autowired
    private InscripcionService inscripcionService;


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


        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            usuarioService.registrarUsuario(dto2);
        });
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

        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            eventoService.crearEvento(eventoDto);
        });
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
        assertEquals("Biblioteca", resultado.get(0).getLugar());

    }


    /*----------------------------------------------------------------*/
    //TEST - 3. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void filtrarEventosNegativo() {
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

        assertThrows(ElementoNoEncontradoException.class, () ->
                eventoService.obtenerConFiltros("Salón de actos", null)
        );
    }

    /*----------------------------------------------------------------*/
    //TEST - 4. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void obtenerDetalles() {
        //GIVEN
        Usuario creador = new Usuario();
        creador.setEmail("creador@test.com");
        creador.setNombre("Pedro");
        creador.setPassword("1234");
        creador.setPrimerApellido("Lopez");
        creador = usuarioService.guardar(creador);

        //EVENTOS CREADO
        CrearEventoDTO evento1 = new CrearEventoDTO();

        evento1.setNombre("Torneo de Ajedrez");
        evento1.setDescripcion("Torneo para todos los alumnos");
        evento1.setFecha(LocalDateTime.now());
        evento1.setLugar("Biblioteca");
        evento1.setRequisitos("Inscripción obligatoria");
        evento1.setPrecio(5.0);
        evento1.setUrlImagen("https://imagen.jpg");
        evento1.setUsuarioId(creador.getId());

        //GUARDAR EVENTO EN LA BD
        EventoDTO eventoCreado = eventoService.crearEvento(evento1);
        Integer idEvento = (eventoCreado.getId());

        EventoDTO eventoObtenido = eventoService.obtenerPorId(idEvento);

        assertNotNull(eventoObtenido);
        assertEquals(idEvento, eventoObtenido.getId());
        assertEquals("Torneo de Ajedrez", eventoObtenido.getNombre());
        assertEquals("Biblioteca", eventoObtenido.getLugar());
    }


    /*----------------------------------------------------------------*/
    //TEST - 4. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void obtenerDetallesNegativo() {
        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            eventoService.obtenerPorId(-1);
        });
        assertEquals(exception.getMessage(), "Evento no encontrado");
    }

    /*----------------------------------------------------------------*/
    //TEST - 5. POSITIVO
    /*----------------------------------------------------------------*/

    @Test
    void modificarEvento() {
        //PRIMERO CREAMOS EL USUARIO CREADOR DEL EVENTO
        Usuario creador = new Usuario();
        creador.setEmail("creador@test.com");
        creador.setNombre("Pedro");
        creador.setPassword("1234");
        creador.setPrimerApellido("Lopez");
        creador = usuarioService.guardar(creador);

        //CREAMOS EVENTO
        CrearEventoDTO evento1 = new CrearEventoDTO();

        evento1.setNombre("Torneo de Ajedrez");
        evento1.setDescripcion("Torneo para todos los alumnos");
        evento1.setFecha(LocalDateTime.now());
        evento1.setLugar("Biblioteca");
        evento1.setRequisitos("Inscripción obligatoria");
        evento1.setPrecio(5.0);
        evento1.setUrlImagen("https://imagen.jpg");
        evento1.setUsuarioId(creador.getId());

        //GUARDAR EVENTO EN LA BD
        EventoDTO eventoCreado = eventoService.crearEvento(evento1);
        Integer idEvento = (eventoCreado.getId());
        Integer idCreadorOriginal = eventoCreado.getUsuarioId();

        //DTO de modificación
        ModificarEventoDTO modificarDTO = new ModificarEventoDTO();
        modificarDTO.setNombre("Torneo de Ajedrez Avanzado");
        modificarDTO.setDescripcion("Torneo solo para alumnos avanzados");
        modificarDTO.setFecha(LocalDateTime.now().plusDays(1));
        modificarDTO.setLugar("Sala de actos");
        modificarDTO.setRequisitos("Nivel avanzado");
        modificarDTO.setPrecio(10.0);
        modificarDTO.setUrlImagen("https://imagen-nueva.jpg");

        //Guardar evento modificado

        EventoDTO eventoModificado = eventoService.modificarEvento(idEvento, modificarDTO);

        assertNotNull(eventoModificado);
        assertEquals(idEvento, eventoModificado.getId());
        assertEquals("Torneo de Ajedrez Avanzado", eventoModificado.getNombre());
        assertEquals("Sala de actos", eventoModificado.getLugar());
        assertEquals(10.0, eventoModificado.getPrecio());

    }



    /*----------------------------------------------------------------*/
    //TEST - 5. NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void modificarEventoNegativo() {

        //DTO DE MODIFICACION

        ModificarEventoDTO modificarDTO = new ModificarEventoDTO();
        modificarDTO.setNombre("Torneo de Ajedrez Avanzado");
        modificarDTO.setDescripcion("Torneo solo para alumnos avanzados");
        modificarDTO.setFecha(LocalDateTime.now().plusDays(1));
        modificarDTO.setLugar("Sala de actos");
        modificarDTO.setRequisitos("Nivel avanzado");
        modificarDTO.setPrecio(10.0);
        modificarDTO.setUrlImagen("https://imagen-nueva.jpg");


        //CUANDO EL ID ES INEXISTENTE
        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            eventoService.modificarEvento(-1,modificarDTO );
        });
        assertEquals(exception.getMessage(), "Evento no encontrado");

    }

    /*----------------------------------------------------------------*/
    //TEST - 6. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void inscripcionEvento(){
        //CREACION USUARIO
        Usuario user = new Usuario();
        user.setEmail("creador@test.com");
        user.setNombre("Pedro");
        user.setPassword("1234");
        user.setPrimerApellido("Lopez");
        user = usuarioService.guardar(user);

        //CREACION EVENTO
        CrearEventoDTO evento1 = new CrearEventoDTO();
        evento1.setNombre("Torneo de Ajedrez");
        evento1.setDescripcion("Torneo para todos los alumnos");
        evento1.setFecha(LocalDateTime.now());
        evento1.setLugar("Biblioteca");
        evento1.setRequisitos("Inscripción obligatoria");
        evento1.setPrecio(5.0);
        evento1.setUrlImagen("https://imagen.jpg");
        evento1.setUsuarioId(user.getId());



        //GUARDAMOS EN LA BD
        EventoDTO eventoGuardado = eventoService.crearEvento(evento1);
        Integer idEvento = eventoGuardado.getId();


        //CREAMOS LA INSCRIPCION
        InscripcionDTO inscripcionDTO = new InscripcionDTO();
        inscripcionDTO.setUsuarioId(user.getId());
        inscripcionDTO.setEventoId(idEvento);
        inscripcionDTO.setFechaInscripcion(LocalDateTime.now().toString());

        //Inscribimos al usuario en el evento
        Inscripcion inscribir = inscripcionService.registrarUsuario(user.getId(), inscripcionDTO);

        assertNotNull(inscribir);
        assertEquals(user.getId(), inscribir.getUsuario().getId());
        assertEquals(idEvento, inscribir.getEvento().getId());

    }

    /*----------------------------------------------------------------*/
    //TEST - 6. NEGATIVO
    /*----------------------------------------------------------------*/

}





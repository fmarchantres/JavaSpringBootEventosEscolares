package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.dto.*;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestIntegrados {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;
    @Autowired
    private InscripcionService inscripcionService;
    @Autowired
    private FotoService fotoService;

    @Autowired
    private UsuarioRepository usuarioRepository;



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

    @Test
    void inscripcionEventoNegativo(){
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
        inscripcionService.registrarUsuario(user.getId(), inscripcionDTO);

        Usuario finalUser = user; //daba pete si no la hacía final
        ElementoNoEncontradoException exception = assertThrows(
                ElementoNoEncontradoException.class,
                () -> inscripcionService.registrarUsuario(finalUser.getId(), inscripcionDTO)
        );

        assertEquals("El usuario ya está inscrito en este evento", exception.getMessage());
    }


    /*----------------------------------------------------------------*/
    //TEST - 7. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void subirFotos(){
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

        //CREACION FOTO
        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setUrl("https://imagen.jpg");
        fotoDTO.setDescripcion("Foto del torneo de ajedrez");
        fotoDTO.setUsuarioId(user.getId());
        fotoDTO.setEventoId(idEvento);
        fotoDTO.setFechaSubida(LocalDateTime.now().toString());


        FotoDTO fotoGuardada = fotoService.subirFotoAGaleria(idEvento, fotoDTO);

        assertNotNull(fotoGuardada);
        assertNotNull(fotoGuardada.getId());
        assertEquals(user.getId(), fotoGuardada.getUsuarioId());
        assertEquals(idEvento, fotoGuardada.getEventoId());
    }


    /*----------------------------------------------------------------*/
    //TEST - 7. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void subirFotosNegativo() {
        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            fotoService.subirFotoAGaleria(-1, new FotoDTO());
        });
        assertEquals(exception.getMessage(), "Evento no encontrado");
    }

    /*----------------------------------------------------------------*/
    //TEST - 8. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void usuarioParticipa(){
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
        inscripcionService.registrarUsuario(user.getId(), inscripcionDTO);

        //Obtener eventos en los que participa el usuario
        List<EventoDTO> eventos = usuarioService.obtenerEventosUsuario(user.getId());

        assertNotNull(eventos);
        assertEquals(1, eventos.size());
        assertEquals(idEvento, eventos.get(0).getId());
        assertEquals("Torneo de Ajedrez",eventos.get(0).getNombre());

    }



    /*----------------------------------------------------------------*/
    //TEST - 8. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void usuarioParticipaNegativo(){
        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> {
            usuarioService.obtenerEventosUsuario(-1);
        });
        assertEquals(exception.getMessage(), "Usuario no encontrado");
    }



    /*----------------------------------------------------------------*/
    //TEST - 9. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void estadisticasEventosTop5() {

        // ---------- USUARIO CREADOR ----------
        Usuario creador = new Usuario();
        creador.setEmail("creador@test.com");
        creador.setNombre("Pedro");
        creador.setPassword("1234");
        creador.setPrimerApellido("Lopez");
        creador = usuarioService.guardar(creador);

        // ---------- EVENTOS ----------
        CrearEventoDTO eventoA = new CrearEventoDTO();
        eventoA.setNombre("Evento A");
        eventoA.setDescripcion("Evento A");
        eventoA.setFecha(LocalDateTime.now());
        eventoA.setLugar("Lugar A");
        eventoA.setRequisitos("Req");
        eventoA.setPrecio(5.0);
        eventoA.setUrlImagen("imgA");
        eventoA.setUsuarioId(creador.getId());

        CrearEventoDTO eventoB = new CrearEventoDTO();
        eventoB.setNombre("Evento B");
        eventoB.setDescripcion("Evento B");
        eventoB.setFecha(LocalDateTime.now());
        eventoB.setLugar("Lugar B");
        eventoB.setRequisitos("Req");
        eventoB.setPrecio(5.0);
        eventoB.setUrlImagen("imgB");
        eventoB.setUsuarioId(creador.getId());

        CrearEventoDTO eventoC = new CrearEventoDTO();
        eventoC.setNombre("Evento C");
        eventoC.setDescripcion("Evento C");
        eventoC.setFecha(LocalDateTime.now());
        eventoC.setLugar("Lugar C");
        eventoC.setRequisitos("Req");
        eventoC.setPrecio(5.0);
        eventoC.setUrlImagen("imgC");
        eventoC.setUsuarioId(creador.getId());

        EventoDTO evA = eventoService.crearEvento(eventoA);
        EventoDTO evB = eventoService.crearEvento(eventoB);
        EventoDTO evC = eventoService.crearEvento(eventoC);

        // ---------- USUARIOS ASISTENTES ----------
        Usuario u1 = usuarioService.guardar(new Usuario(null,"u1@test.com",null,"U1","1234","Test",null,null,null));
        Usuario u2 = usuarioService.guardar(new Usuario(null,"u2@test.com",null,"U2","1234","Test",null,null,null));
        Usuario u3 = usuarioService.guardar(new Usuario(null,"u3@test.com",null,"U3","1234","Test",null,null,null));
        Usuario u4 = usuarioService.guardar(new Usuario(null,"u4@test.com",null,"U4","1234","Test",null,null,null));
        Usuario u5 = usuarioService.guardar(new Usuario(null,"u5@test.com",null,"U5","1234","Test",null,null,null));
        Usuario u6 = usuarioService.guardar(new Usuario(null,"u6@test.com",null,"U6","1234","Test",null,null,null));

        // ---------- INSCRIPCIONES ----------
        // Evento A → 1 asistente
        InscripcionDTO insA = new InscripcionDTO();
        insA.setUsuarioId(u1.getId());
        insA.setEventoId(evA.getId());
        insA.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evA.getId(), insA);

        // Evento B → 3 asistentes
        InscripcionDTO insB1 = new InscripcionDTO();
        insB1.setUsuarioId(u2.getId());
        insB1.setEventoId(evB.getId());
        insB1.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evB.getId(), insB1);

        InscripcionDTO insB2 = new InscripcionDTO();
        insB2.setUsuarioId(u3.getId());
        insB2.setEventoId(evB.getId());
        insB2.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evB.getId(), insB2);

        InscripcionDTO insB3 = new InscripcionDTO();
        insB3.setUsuarioId(u4.getId());
        insB3.setEventoId(evB.getId());
        insB3.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evB.getId(), insB3);

        // Evento C → 2 asistentes
        InscripcionDTO insC1 = new InscripcionDTO();
        insC1.setUsuarioId(u5.getId());
        insC1.setEventoId(evC.getId());
        insC1.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evC.getId(), insC1);

        InscripcionDTO insC2 = new InscripcionDTO();
        insC2.setUsuarioId(u6.getId());
        insC2.setEventoId(evC.getId());
        insC2.setFechaInscripcion(LocalDateTime.now().toString());
        inscripcionService.registrarUsuario(evC.getId(), insC2);

        // ---------- WHEN ----------
        List<EstadisticasDTO> ranking = inscripcionService.obtenerEstadisticasEventos();

        // ---------- THEN ----------
        assertNotNull(ranking);
        assertEquals(3, ranking.size());

        assertEquals(evB.getId(), ranking.get(0).getId());
        assertEquals(3L, ranking.get(0).getTotal_asistentes());

        assertEquals(evC.getId(), ranking.get(1).getId());
        assertEquals(2L, ranking.get(1).getTotal_asistentes());

        assertEquals(evA.getId(), ranking.get(2).getId());
        assertEquals(1L, ranking.get(2).getTotal_asistentes());
    }



    /*----------------------------------------------------------------*/
    //TEST - 9. NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void estadisticasEventosTop5Negativo(){
        List<EstadisticasDTO> ranking = inscripcionService.obtenerEstadisticasEventos();

        assertNotNull(ranking);
        assertTrue(ranking.isEmpty());
    }


    /*----------------------------------------------------------------*/
    //TEST - 10. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void usuarioActivo() {

        // GIVEN ─ Usuarios
        Usuario u1 = new Usuario();
        u1.setEmail("u1@test.com");
        u1.setNombre("Usuario 1");
        u1.setPassword("1234");
        u1.setPrimerApellido("A");
        u1 = usuarioService.guardar(u1);

        Usuario u2 = new Usuario();
        u2.setEmail("u2@test.com");
        u2.setNombre("Usuario 2");
        u2.setPassword("1234");
        u2.setPrimerApellido("B");
        u2 = usuarioService.guardar(u2);

        // GIVEN ─ Eventos creados
        CrearEventoDTO e1 = new CrearEventoDTO("Torneo de Ajedrez", "Competición escolar abierta a todos los alumnos", LocalDateTime.now(), "Salón de actos", "Inscripción obligatoria", 0.0, u1.getId(), "ajedrez.jpg");
        CrearEventoDTO e2 = new CrearEventoDTO("Charla de Orientación", "Sesión informativa sobre salidas profesionales", LocalDateTime.now(), "Aula Magna", "Acceso libre", 0.0, u1.getId(), "orientacion.jpg");
        eventoService.crearEvento(e1);
        eventoService.crearEvento(e2);

        CrearEventoDTO e3 = new CrearEventoDTO("Club de Lectura", "Encuentro mensual para comentar libros", LocalDateTime.now(), "Biblioteca del centro", "Plazas limitadas", 0.0, u2.getId(), "club_lectura.jpg");
        EventoDTO ev3 = eventoService.crearEvento(e3);

        // GIVEN ─ Inscripciones
        InscripcionDTO ins = new InscripcionDTO();
        ins.setUsuarioId(u2.getId());
        ins.setEventoId(ev3.getId());
        inscripcionService.registrarUsuario(ev3.getId(), ins);

        // WHEN
        UsuarioEstadisticaDTO resultado = usuarioRepository.findEstadisticaUsuario();

        // THEN
        assertNotNull(resultado);
        assertEquals(u1.getId(), resultado.getId());
        assertEquals("Usuario 1", resultado.getNombre());
        assertEquals(2L, resultado.getTotal_eventos());
    }


    /*----------------------------------------------------------------*/
    // TEST - 10. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void estadisticaUsuarioActivoNegativo() {
        //No hay usuarios ni actividad
        UsuarioEstadisticaDTO resultado = usuarioRepository.findEstadisticaUsuario();

        //No se devuelve ningún usuario activo
        assertNull(resultado);
    }
}





package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;



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
}


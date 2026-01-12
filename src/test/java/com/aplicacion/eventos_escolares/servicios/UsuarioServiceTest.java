package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    void registrarUsuario_Positive() {

        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("prueba@test.com");
        dto.setNombre("Fran");
        dto.setPassword("1234");
        dto.setPrimerApellido("López");
        dto.setSegundoApellido("Gómez");

        Usuario usuario = usuarioService.registrarUsuario(dto);

        assertNotNull(usuario.getId());
        assertEquals("prueba@test.com", usuario.getEmail());
        assertNotNull(usuario.getFechaRegistro());
    }
}


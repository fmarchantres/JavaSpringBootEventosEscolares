package com.aplicacion.eventos_escolares;

import com.aplicacion.eventos_escolares.modelos.Usuarios;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsuariosRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testGuardarYBuscarUsuario() {
        // Crear un nuevo usuario
        Usuarios usuario = new Usuarios();
        usuario.setNombre("Fran");
        usuario.setApellidos("Martín");
        usuario.setEmail("fran@example.com");
        usuario.setPassword("1234");

        // Guardarlo en la base de datos
        usuarioRepository.save(usuario);

        // Buscarlo por email
        Usuarios resultado = usuarioRepository.findByEmail("fran@example.com");

        // Comprobar que se ha guardado correctamente
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Fran");
    }
}

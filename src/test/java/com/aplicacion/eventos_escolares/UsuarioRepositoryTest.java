package com.aplicacion.eventos_escolares;

import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testGuardarYBuscarUsuario() {
        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Fran");
        usuario.setPrimerApellido("Martín");
        usuario.setSegundoApellido("Gómez");
        usuario.setEmail("fran@example.com");
        usuario.setPassword("1234");

        // Guardarlo en la base de datos
        usuarioRepository.save(usuario);

        // Buscarlo por email
        Usuario resultado = usuarioRepository.findByEmail("fran@example.com").orElse(null);

        // Comprobar que se ha guardado correctamente
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Fran");
    }
}

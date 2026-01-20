package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsuarioServiceMockitoTest {

    @Mock //Mock simula o finje el comportamiento
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private RegistrarUsuarioMapper registrarUsuarioMapper;

    @Autowired
    private EventoService eventoService;

    @Mock
    private EventoMapper eventoMapper;

    @Mock
    private EventoRepository eventoRepository;


    /*----------------------------------------------------------------*/
    //TEST - 1.
    /*----------------------------------------------------------------*/
    @Test
    @DisplayName("Test de integracion Nº1")
    void registrarUsuarioEmailDuplicado() {
        //GIVEN
        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("email@hotmail.com");
        dto.setNombre("Fran");
        dto.setPassword("123456");
        dto.setPrimerApellido("Martinez");

        //Simulamos que el email ya existe
        when(usuarioRepository.existsByEmail("email@hotmail.com")).thenReturn(true);

        ElementoNoEncontradoException exception = assertThrows(ElementoNoEncontradoException.class, () -> usuarioService.registrarUsuario(dto));
        assertEquals("El email ya está registrado", exception.getMessage());

        //Verificamos que no intentar guardar nada
        verify(usuarioRepository, never()).save(any());
    }

    /*----------------------------------------------------------------*/
    //TEST - 2.
    /*----------------------------------------------------------------*/
    @Test
    @DisplayName("Test de integración Nº2")
    void crearEvento(){


    }

}

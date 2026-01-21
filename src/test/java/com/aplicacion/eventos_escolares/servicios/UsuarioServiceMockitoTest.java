package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceMockitoTest {

    //Instancia real de UsuarioService
    @InjectMocks
    private UsuarioService usuarioService;

    //No es real, mockito lo simula
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RegistrarUsuarioMapper registrarUsuarioMapper;




    /*----------------------------------------------------------------*/
    //TEST - 1. REGISTRAR USUARIO
    /*----------------------------------------------------------------*/
    @Test
    void registrarUsuarioEmail(){
        //GIVEN
        //Creamos el DTO que llega desde el controlador
        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("email@hotmail.com");
        dto.setNombre("Fran");
        dto.setPassword("1234");
        dto.setPrimerApellido("López");
        dto.setSegundoApellido("Gómez");

        //Creamos un usuario que el mapper debería devolver
        Usuario usuarioMapeado = new Usuario();
        usuarioMapeado.setEmail(dto.getEmail());

        //Creamos el usuario que el repositorio devolverá al guardar
        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1);
        usuarioGuardado.setEmail(dto.getEmail());
        usuarioGuardado.setFechaRegistro(LocalDateTime.now());

        //MOCKS NECESARIO
        //Cuando el servicio llame al mapper, devolvemos usuarioMapeado
        when(registrarUsuarioMapper.toEntity(dto)).thenReturn(usuarioMapeado);

        //Cuando el servicio llame al repositorio para guardar, devolvemos usuarioGuardado
        when(usuarioRepository.save(usuarioMapeado)).thenReturn(usuarioGuardado);

        //EJECUCION DEL METODO A PROBAR
        Usuario resultado = usuarioService.registrarUsuario(dto);

        //HACEMOS LAS COMPROBACIONES
        //Verificamos que el usuario tenga ID simulado por el mock
        assertNotNull(resultado.getId());

        // Verificamos que el email es el esperado
        assertEquals("email@hotmail.com", resultado.getEmail());
    }

    /*----------------------------------------------------------------*/
    //TEST - 1. REGISTRAR USUARIO EMAIL DUPLICADO
    /*----------------------------------------------------------------*/

    @Test
    void registrarUsuarioEmailNegativo(){
        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("email@hotmail.com"); //email duplicado

        //Simulamos que ya existe un usuario con ese email
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("email@hotmail.com");

        // Cuando el repositorio busque ese email, devolverá un usuario existente
        when(usuarioRepository.existsByEmail("email@hotmail.com")).thenReturn(true);

        //Comprobamos la excepcion
        ElementoNoEncontradoException excepcion = assertThrows(ElementoNoEncontradoException.class, () -> usuarioService.registrarUsuario(dto));

        //Comprobamos que el mensaje es el esperado
        assertEquals("El email ya está registrado", excepcion.getMessage());

    }
}

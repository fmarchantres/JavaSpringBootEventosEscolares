package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    //TEST - 1. REGISTRAR USUARIO POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void registrarUsuarioEmail(){

        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("email22@hotmail.com");

        when (usuarioRepository.existsByEmail("email22@hotmail.com"))
                .thenReturn(false);

        when(registrarUsuarioMapper.toEntity(dto))
                .thenReturn(new Usuario());

        when (usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(new Usuario());

        this.usuarioService.registrarUsuario(dto);

        Mockito.verify(usuarioRepository, Mockito.times(1)).existsByEmail("email22@hotmail.com");
        Mockito.verify(registrarUsuarioMapper, Mockito.times(1)).toEntity(dto);
        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any(Usuario.class));


    }

    /*----------------------------------------------------------------*/
    //TEST - 1. REGISTRAR USUARIO EMAIL DUPLICADO NEGATIVO
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


    /*----------------------------------------------------------------*/
    //TEST - 8. NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void eventosUsuarioParticipa(){
        when(usuarioRepository.findById(Mockito.anyInt()))
                .thenThrow(ElementoNoEncontradoException.class);

        assertThrows(ElementoNoEncontradoException.class,
                () -> usuarioService.obtenerEventosUsuario(Mockito.anyInt()));

        verify(usuarioRepository, Mockito.times(1)).findById(Mockito.anyInt());

    }







}

package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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

    /*
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

     */

    /*----------------------------------------------------------------*/
    //TEST - 1. REGISTRAR USUARIO EMAIL DUPLICADO NEGATIVO
    /*----------------------------------------------------------------*/

    @Test
    void registrarUsuarioEmailNegativo(){
        RegistrarUsuarioDTO dto = new RegistrarUsuarioDTO();
        dto.setEmail("email@hotmail.com"); //email duplicado


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

        when(usuarioRepository.findById(-1))
                .thenReturn(Optional.empty());

        assertThrows(ElementoNoEncontradoException.class,
                () -> usuarioService.obtenerEventosUsuario(-1));

        verify(usuarioRepository, Mockito.times(1)).findById(-1);
        verify(usuarioRepository, never()).save(any()); //Verifica que nunca se llama al metodo save

    }

    /*----------------------------------------------------------------*/
    //TEST - 10. POSITIVO
    /*----------------------------------------------------------------*/
    @Test
    void consultaSQL2(){
        //Creamos el Mock del DTO
        UsuarioEstadisticaDTO dto = Mockito.mock(UsuarioEstadisticaDTO.class);

        //El mock devieñve el objeto cuando el servicio lo solicite
        when(usuarioRepository.findEstadisticaUsuario()).thenReturn(dto);

        //Ejecutamos el metodo del servicio que contiene la lógica de la consulta
        this.usuarioService.registrarEstadisticaUsuario();

        //Verificamos que el servicio invocó al repositorio una única vez
        Mockito.verify(usuarioRepository, Mockito.times(1)).findEstadisticaUsuario();
    }
}

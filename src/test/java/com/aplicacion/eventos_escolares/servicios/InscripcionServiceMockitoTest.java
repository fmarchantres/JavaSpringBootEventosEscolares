package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.InscripcionMapper;
import com.aplicacion.eventos_escolares.dto.EstadisticasDTO;
import com.aplicacion.eventos_escolares.dto.InscripcionDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import com.aplicacion.eventos_escolares.repositories.InscripcionRepository;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceMockitoTest {

    @InjectMocks
    private InscripcionService inscripcionService;

    @Mock
    private InscripcionRepository inscripcionRepository;

    @Mock
    private InscripcionMapper inscripcionMapper;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;



    /*----------------------------------------------------------------*/
    //TEST - 6. POSITIVO
    /*----------------------------------------------------------------*/

    @Test
    void inscripcionUsuario() {

        InscripcionDTO dto = new InscripcionDTO();
        dto.setUsuarioId(1);


        when(inscripcionRepository.existsByUsuarioIdAndEventoId(1, 1))
                .thenReturn(false);

        when(eventoRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Evento()));

        when(usuarioRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(new Usuario()));

        when(inscripcionMapper.toEntity(Mockito.any(InscripcionDTO.class)))
                .thenReturn(new Inscripcion());

        when(inscripcionRepository.save(Mockito.any(Inscripcion.class)))
                .thenReturn(new Inscripcion());


        inscripcionService.registrarUsuario(1,dto);

        Mockito.verify(inscripcionRepository, Mockito.times(1))
                .existsByUsuarioIdAndEventoId(1, 1);

        Mockito.verify(eventoRepository, Mockito.times(1))
                .findById(1);

        Mockito.verify(usuarioRepository, Mockito.times(1))
                .findById(1);

        Mockito.verify(inscripcionRepository, Mockito.times(1))
                .save(Mockito.any(Inscripcion.class));
    }




}

package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class FotoServiceMockitoTest {

    @InjectMocks
    private FotoService fotoService;

    @Mock
    private EventoRepository eventoRepository;



    /*----------------------------------------------------------------*/
    //TEST - 7. NEGATIVO
    /*----------------------------------------------------------------*/
    @Test
    void subirFotoNegativo(){

        when(eventoRepository.findById(Mockito.anyInt()))
                .thenThrow(ElementoNoEncontradoException.class);

        assertThrows(ElementoNoEncontradoException.class, () ->
                fotoService.subirFotoAGaleria(1, Mockito.mock(FotoDTO.class)));

        Mockito.verify(eventoRepository, Mockito.times(1));

    }

}

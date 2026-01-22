package com.aplicacion.eventos_escolares.servicios;


import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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

        //Comportamiento si no lo encuentra
        when(eventoRepository.findById(10))
                .thenReturn(Optional.empty());


        assertThrows(ElementoNoEncontradoException.class, () ->
                fotoService.subirFotoAGaleria(10, Mockito.mock(FotoDTO.class)));

        //Especificamos que metodo se tuvo que llamar
        Mockito.verify(eventoRepository, Mockito.times(1)).findById(10);

    }

}

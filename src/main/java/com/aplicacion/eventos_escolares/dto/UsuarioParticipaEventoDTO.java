package com.aplicacion.eventos_escolares.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioParticipaEventoDTO {

    private Integer eventoId;
    private String eventoNombre;
    private Integer usuarioId;
    private String usuarioNombre;



}

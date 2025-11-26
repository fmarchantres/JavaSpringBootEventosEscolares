package com.aplicacion.eventos_escolares.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscripcionDTO {

    private Integer id;
    private Integer usuarioId;
    private Integer eventoId;
    private String fechaInscripcion;
}

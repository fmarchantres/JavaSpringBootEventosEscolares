package com.aplicacion.eventos_escolares.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoDTO {

    private Integer id;
    private String descripcion;
    private String url;
    private String fechaSubida;
    private Integer usuarioId;
    private Integer eventoId;
}

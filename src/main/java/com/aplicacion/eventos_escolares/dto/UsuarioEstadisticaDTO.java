package com.aplicacion.eventos_escolares.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEstadisticaDTO {
    private Integer creadorId;
    private Long numero_eventos;

}

package com.aplicacion.eventos_escolares.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasDTO {
    private Integer id;
    private String nombre; //TIENEN QUE COINCIDIR EXACTAMENTE CON EL NOMBRE DEL ALIAS DE LA CONSULTA SQL
    private Long total_asistentes;

}

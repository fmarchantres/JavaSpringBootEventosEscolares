package com.aplicacion.eventos_escolares.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventoDetallesDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String lugar;
    private LocalDateTime fecha;
    private String requisitos;
    private Double precio;
    private Integer creadorId;
}

package com.aplicacion.eventos_escolares.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearEventoDTO {
    private String nombre;
    private String descripcion;
    private String lugar;
    private String fecha;
    private String requisitos;
    private Double precio;

    private Integer creadorId;
}

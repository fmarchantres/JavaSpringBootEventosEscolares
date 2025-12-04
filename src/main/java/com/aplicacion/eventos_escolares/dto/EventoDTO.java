package com.aplicacion.eventos_escolares.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventoDTO {

    @NotNull
    private Integer id;

    @NotNull
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private String lugar;
    private String requisitos;
    private Double precio;


    private Integer usuarioId; //Guardamos el ID del creador del evento (en lugar del objeto entero eventos)

}

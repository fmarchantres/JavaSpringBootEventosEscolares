package com.aplicacion.eventos_escolares.dto;


import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CrearEventoDTO {

    @NotBlank (message = "Tienes que introducir un nombre")
    private String nombre;

    @NotBlank (message = "Tienes que introducir una descripción")
    private String descripcion;

    @FutureOrPresent (message = "La fecha debe ser hoy o en el futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull (message = "Tienes que introducir una fecha, debe ser hoy o futura")
    private LocalDateTime fecha;

    @NotBlank (message = "Tienes que introducir un lugar")
    private String lugar;

    //@NotBlank (message = "Tienes que introducir los requisitos")
    private String requisitos;

    private Double precio;

    @NotNull (message = "Tienes que introducir el id del creador")
    private Integer usuarioId; //Guardamos el ID del creador del evento (en lugar del objeto entero eventos)

    private String urlImagen;

}



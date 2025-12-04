package com.aplicacion.eventos_escolares.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ModificarEventoDTO {

    @NotBlank (message = "Tienes que introducir una descripción")
    private String descripcion;

    @FutureOrPresent(message = "La fecha debe ser hoy o en el futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;

    @NotBlank(message = "Tienes que introducir un lugar")
    private String lugar;

}

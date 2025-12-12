package com.aplicacion.eventos_escolares.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioParticipaEventoDTO {

    @NotNull(message = "Tienes que introducir un evento")
    private Integer eventoId;
    @NotBlank(message = "Tienes que introducir un nombre de Evento")
    private String eventoNombre;
    @NotNull(message = "Tienes que introducir un usuario")
    private Integer usuarioId;
    private String usuarioNombre;



}

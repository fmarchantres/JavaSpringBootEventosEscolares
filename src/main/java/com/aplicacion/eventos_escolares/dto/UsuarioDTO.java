package com.aplicacion.eventos_escolares.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {



    @NotNull
    private Integer id;

    @NotNull
    private String email;


    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaRegistro;

    @NotNull
    private String nombre;

    @NotNull
    private String primerApellido;

    private String segundoApellido;


    private List<EventoDTO> eventos;
}

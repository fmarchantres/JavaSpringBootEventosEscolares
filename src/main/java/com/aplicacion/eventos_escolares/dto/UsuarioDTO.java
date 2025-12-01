package com.aplicacion.eventos_escolares.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {
    private Integer id;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime fechaRegistro;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    private List<EventoDTO> eventos;
}

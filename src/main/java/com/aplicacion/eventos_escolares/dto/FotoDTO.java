package com.aplicacion.eventos_escolares.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoDTO {

    private Integer id;

    @NotBlank(message = "Tienes que introducir una descripción")
    private String descripcion;
    @NotBlank (message = "Tienes que introducir una URL")
    private String url;
    private String fechaSubida;
    @NotNull (message = "Tienes que introducir un usuario")
    private Integer usuarioId;
    private Integer eventoId;
}

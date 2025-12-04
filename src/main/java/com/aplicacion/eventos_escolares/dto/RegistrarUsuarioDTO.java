package com.aplicacion.eventos_escolares.dto;


import jakarta.validation.constraints.Email;
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

public class RegistrarUsuarioDTO {

    @Email
    @NotBlank (message = "Tienes que introducir un email")
    private String email;

    //private LocalDateTime fechaRegistro;
    @NotBlank (message = "Tienes que introducir un nombre")
    private String nombre;

    @NotBlank (message = "Tienes que introducir una contraseña")
    private String password;

    @NotBlank (message = "Tienes que introducir al menos un apellido")
    private String primerApellido;

    private String segundoApellido;


}

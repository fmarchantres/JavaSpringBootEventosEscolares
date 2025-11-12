package com.aplicacion.eventos_escolares.modelos;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
// @Table(name = "usuarios", schema = "eventos",catalog = "localhost")


public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column (name = "segundo_apellido")
    private String segundoApellido;

    @Column (name = "email")
    private String email;

    @Column (name = "password")
    private String password;

    @Column (name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();


}

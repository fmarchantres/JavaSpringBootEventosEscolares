package com.aplicacion.eventos_escolares.modelos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuarios")


public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "email")
    private String email;

    @Column (name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "password")
    private String password;

    @Column (name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column (name = "segundo_apellido")
    private String segundoApellido;


    //Relacion con evento (FK)
    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Evento> eventos;


    //Relacion con inscripciones
    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inscripcion> inscripciones;


}

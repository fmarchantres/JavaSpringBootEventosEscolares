package com.aplicacion.eventos_escolares.modelos;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "contactos")

public class Contactos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "nombre")
    private String nombre;

    @Column (name = "apellido")
    private String apellido;

    @Column (name = "email")
    private String email;

    @Column (name = "mensaje", nullable = false)
    private String mensaje;

    @Column (name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}

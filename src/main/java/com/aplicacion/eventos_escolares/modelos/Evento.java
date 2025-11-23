package com.aplicacion.eventos_escolares.modelos;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "eventos")

public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;

    @Column(name = "lugar")
    private String lugar;

    @Column(name = "requisitos")
    private String requisitos;

    @Column(name = "precio")
    private Double precio;




    

    //Relacion con usuarios (FK)
    @ManyToOne
    @JoinColumn (name = "creador_id", nullable = false)
    private Usuarios creador;

}
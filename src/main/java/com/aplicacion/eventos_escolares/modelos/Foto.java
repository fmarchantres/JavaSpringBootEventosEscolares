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
@Table(name = "fotos")

public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ruta", nullable = false)
    private String url;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida = LocalDateTime.now();


    //Relacion con usuarios 
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    //Relación con eventos
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;




}

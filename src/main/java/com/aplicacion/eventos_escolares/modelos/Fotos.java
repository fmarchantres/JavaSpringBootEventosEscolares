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
@Table(name = "fotos", schema = "eventos",catalog = "localhost")
public class Fotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //Relacion con usuarios (FK)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    //Relación con eventos (FK)
    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Eventos evento;

    @Column (name = "ruta")
    private String ruta;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida = LocalDateTime.now();
}

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
@Table(name = "eventos", schema = "eventos",catalog = "localhost")

public class Eventos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "lugar")
    private String lugar;

    @Column(name = "hora")
    private LocalDateTime hora;

    @Column(name = "requisitos")
    private String requisitos;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "cupo_maximo")
    private Integer cupoMaximo;

    //Relacion con usuarios (FK)
    @ManyToOne
    @JoinColumn (name = "creador_id", nullable = false)
    private Usuarios creador;

}
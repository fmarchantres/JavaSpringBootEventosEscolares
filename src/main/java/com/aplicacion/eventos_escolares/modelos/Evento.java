package com.aplicacion.eventos_escolares.modelos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;


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

    @Column(name = "descripcion" ,  nullable = false)
    private String descripcion;

    @Column(name = "fecha")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fecha;

    @Column(name = "lugar", nullable = false)
    private String lugar;

    @Column(name = "requisitos", nullable = false)
    private String requisitos;

    @Column(name = "precio")
    private Double precio;

    @Column (name = "url_imagen")
    private String urlImagen;





    //Relacion con usuarios (FK)
    @ManyToOne
    @JoinColumn (name = "creador_id", nullable = false)
    private Usuario creador;


    //Relacion con inscripciones (BORRADO EN CASCADA)
    @OneToMany (mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inscripcion> inscripciones;


    //Relacion con Foto (BORRADO EN CASCADA)
    @OneToMany (mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Foto> fotos;

    //Relacion con notificacion (BORRADO EN CASCACA)
    @OneToMany(
    mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notificaciones> notificaciones;

    //Relacion con pagos
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pagos> pagos;

}
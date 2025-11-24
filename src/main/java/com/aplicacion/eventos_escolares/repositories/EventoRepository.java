package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    //buscar por nombre
    Evento findByNombre(String nombre);

    //buscar por fecha
    List<Evento> findByFecha(LocalDate fecha);

    //Buscar por tramo de fecha
    List<Evento> findByFechaBetween (LocalDateTime inicio, LocalDateTime fin);

    //Buscar por lugar y fecha
    List<Evento> findByLugarContainingIgnoreCaseAndFechaBetween (
            String lugar,
            LocalDateTime inicio,
            LocalDateTime fin);

    //buscar por lugar
    List<Evento> findByLugar (String lugar);

    List<Evento> findByLugarContainingIgnoreCase(String lugar);
}

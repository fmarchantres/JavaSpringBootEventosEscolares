package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {

    //buscar por nombre
    Evento findByNombre(String nombre);

    //buscar por fecha
    List<Evento> findByFecha(LocalDate fecha);

    //buscar por lugar
    List<Evento> findByLugar (String lugar);

    List<Evento> findByLugarContainingIgnoreCase(String lugar);
}

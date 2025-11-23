package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends JpaRepository<Evento, Integer> {

    //buscar por nombre
    Evento findByNombre(String nombre);
}

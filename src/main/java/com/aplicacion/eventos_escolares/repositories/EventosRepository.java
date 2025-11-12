package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Integer> {

    //buscar por nombre
    Eventos findByNombre(String nombre);
}

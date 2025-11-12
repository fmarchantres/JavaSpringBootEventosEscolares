package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Inscripciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionesRepository extends JpaRepository<Inscripciones, Integer> {


}

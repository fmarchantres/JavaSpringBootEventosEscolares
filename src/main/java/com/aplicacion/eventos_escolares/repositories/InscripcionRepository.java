package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    //VERIFICA SI UN USUARIO YA ESTÁ INSCRITO EN UN EVENTO
    boolean existsByUsuarioIdAndEventoId(Integer usuarioId, Integer eventoId);

     List<Inscripcion> findByUsuarioId(Integer usuarioId);


}

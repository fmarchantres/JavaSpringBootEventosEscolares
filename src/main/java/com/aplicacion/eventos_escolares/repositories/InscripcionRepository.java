package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.dto.EstadisticasDTO;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    //VERIFICA SI UN USUARIO YA ESTÁ INSCRITO EN UN EVENTO
    boolean existsByUsuarioIdAndEventoId(Integer usuarioId, Integer eventoId);

     List<Inscripcion> findByUsuarioId(Integer usuarioId);

     //CONSULTA 1 SQL
     @Query(value = "SELECT e.id, e.nombre, COUNT (i.id) as total_asistentes\n" +
             "FROM eventos e\n" +
             "JOIN inscripciones i ON e.id = i.evento_id\n" +
             "GROUP BY e.id, e.nombre\n" +
             "ORDER BY total_asistentes DESC\n" +
             "LIMIT 5;", nativeQuery = true )
    List<EstadisticasDTO> estadisticas();
}

package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);



    //CONSULTA 2 SQL
    @Query(value = """
        SELECT 
            u.id AS id,
            u.nombre AS nombre,
            (
                (SELECT COUNT(*) FROM eventos e WHERE e.creador_id = u.id)
                +
                (SELECT COUNT(*) FROM inscripciones i WHERE i.usuario_id = u.id)
            ) AS total_eventos
        FROM usuarios u
        ORDER BY total_eventos DESC
        LIMIT 1
        """, nativeQuery = true)
    UsuarioEstadisticaDTO findEstadisticaUsuario();



}

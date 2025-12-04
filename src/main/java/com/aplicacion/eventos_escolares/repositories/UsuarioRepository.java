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
    @Query(value ="SELECT creador_id, COUNT(*) as numero_eventos\n" +
            "FROM eventos\n" +
            "GROUP BY creador_id\n" +
            "ORDER BY COUNT(*) DESC\n" +
            "LIMIT 1;" , nativeQuery = true)
    UsuarioEstadisticaDTO findEstadisticaUsuario();

}

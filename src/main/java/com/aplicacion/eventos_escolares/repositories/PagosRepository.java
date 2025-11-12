package com.aplicacion.eventos_escolares.repositories;

import com.aplicacion.eventos_escolares.modelos.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosRepository extends JpaRepository<Pagos, Integer> {
}

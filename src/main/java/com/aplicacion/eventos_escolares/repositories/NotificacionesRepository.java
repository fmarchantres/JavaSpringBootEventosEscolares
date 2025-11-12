package com.aplicacion.eventos_escolares.repositories;


import com.aplicacion.eventos_escolares.modelos.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificaciones, Integer> {
}

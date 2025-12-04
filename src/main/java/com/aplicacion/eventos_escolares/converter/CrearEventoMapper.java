package com.aplicacion.eventos_escolares.converter;


import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper (componentModel = "spring")
public interface CrearEventoMapper {

    //CONVERSION DE DTO A ENTITY
    @Mapping(target = "id", ignore = true) //el id lo genera la BD
    @Mapping(target = "creador", ignore = true) //lo asignamos en el service
    Evento toEntity (CrearEventoDTO dto);

    @Mapping(target = "usuarioId", source = "creador.id")
    EventoDTO toDTO (Evento entity);
}

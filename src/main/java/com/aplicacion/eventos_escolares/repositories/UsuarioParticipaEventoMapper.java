package com.aplicacion.eventos_escolares.repositories;


import com.aplicacion.eventos_escolares.dto.UsuarioParticipaEventoDTO;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface UsuarioParticipaEventoMapper {

    @Mapping(target = "eventoId", source = "evento.id")
    @Mapping(target = "eventoNombre", source = "evento.nombre")
    @Mapping(target = "usuarioId", source = "usuario.id")
    @Mapping(target = "usuarioNombre", source = "usuario.nombre")
    UsuarioParticipaEventoDTO toDTO(Inscripcion inscripcion);
    List<UsuarioParticipaEventoDTO> toDTO(List<Inscripcion> inscripciones);
}

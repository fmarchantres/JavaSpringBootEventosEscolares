package com.aplicacion.eventos_escolares.converter;

import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Usuarios;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface UsuarioMapper {


    UsuarioDTO toDTO (Usuarios usuario); //Esto envia al front
    @Mapping(target = "eventos", ignore = true)
    Usuarios toEntity (UsuarioDTO usuarioDTO); //Esto recibe del front


    List<UsuarioDTO> toDTOList (List<Usuarios> usuarios);
    @Mapping(target = "eventos", ignore = true)
    List<Usuarios> toEntityList (List<UsuarioDTO> usuariosDTO);



}

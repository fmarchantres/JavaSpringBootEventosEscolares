package com.aplicacion.eventos_escolares.converter;

import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface UsuarioMapper {


    UsuarioDTO toDTO (Usuario usuario); //Esto envia al front
    @Mapping(target = "eventos", ignore = true)
    Usuario toEntity (UsuarioDTO usuarioDTO); //Esto recibe del front


    List<UsuarioDTO> toDTOList (List<Usuario> usuarios);
    @Mapping(target = "eventos", ignore = true)
    List<Usuario> toEntityList (List<UsuarioDTO> usuariosDTO);



}

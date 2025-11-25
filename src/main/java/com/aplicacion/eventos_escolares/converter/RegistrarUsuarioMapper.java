package com.aplicacion.eventos_escolares.converter;

import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "spring")
public interface RegistrarUsuarioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "eventos", ignore = true)

    Usuario toEntity (RegistrarUsuarioDTO dto);





}

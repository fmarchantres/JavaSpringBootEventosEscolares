package com.aplicacion.eventos_escolares.converter;


import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.modelos.Foto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface FotoMapper {


    //ENTIDAD -> DTO
    @Mapping (target = "usuarioId" , source = "usuario.id")
    @Mapping (target = "eventoId" , source = "evento.id")
    @Mapping(target = "fechaSubida", source = "fechaSubida", qualifiedByName = "fechaToString")
    FotoDTO toDTO (Foto foto);


    //DTO -> ENTIDAD
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "fechaSubida", expression = "java(java.time.LocalDateTime.now())")
    Foto toEntity (FotoDTO fotoDTO);


    //CONVERSION FECHA
    @Named("fechaToString")
    default String fechaToString(LocalDateTime fecha) {
        if (fecha == null) return null;
        return fecha.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }





}

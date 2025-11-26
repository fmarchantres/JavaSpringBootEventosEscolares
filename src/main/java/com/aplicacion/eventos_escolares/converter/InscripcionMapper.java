package com.aplicacion.eventos_escolares.converter;


import com.aplicacion.eventos_escolares.dto.InscripcionDTO;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper (componentModel = "spring")
public interface InscripcionMapper {

    //ENTIDAD -> DTO

    @Mapping(target = "usuarioId" , source = "usuario.id")
    @Mapping(target = "eventoId" , source = "evento.id")
    @Mapping(target = "fechaInscripcion", source = "fechaInscripcion",qualifiedByName = "fechaToString")
    InscripcionDTO toDTO (Inscripcion inscripcion);





    //DTO -> ENTIDAD
    //MapStruct intentará mapear usuario y evento pero como el DTO solo tiene usuarioId no sabrá como llenarlo
    //por lo que tenemos que ignorarlos.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "evento", ignore = true)
    @Mapping(target = "fechaInscripcion", expression = "java(java.time.LocalDateTime.now())")

    Inscripcion toEntity (InscripcionDTO inscripcionDTO);

    //Conversion de fecha
    @Named("fechaToString")
    default String fechaToString(LocalDateTime fecha) {
        if (fecha == null) return null;

        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return fecha.format(formatter);
    }

}

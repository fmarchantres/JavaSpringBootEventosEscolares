package com.aplicacion.eventos_escolares.converter;

import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

@Mapper (componentModel = "spring")
public interface EventoMapper {
    //CONVIERTE EVENTO A DTO
    @Mapping(target = "usuarioId", source = "creador.id")  //source lo coge de la clase evento de la relación con Usuario
    @Mapping(target = "fecha", source = "fecha", qualifiedByName = "convertirFecha")
    EventoDTO toDTO (Evento evento); //Esto envia al front

    //CONVIERTE DTO A EVENTO, ignoramos creador por que el DTO solo trae un ID, no un usuario completo (lo asignaremos en el service)
    @Mapping(target = "creador", ignore = true)
    @Mapping(target = "fecha", source = "fecha", qualifiedByName = "convertirFechaInversa")
    Evento toEntity (EventoDTO dto); //Esto recibe del front

    //CONVIERTE LISTA DE EVENTOS A LISTA DE DTOS
    @Mapping(target = "usuarioId", source = "creador.id")
    List<EventoDTO> toDTOList (List<Evento> eventos);

    //CONVIERTE LISTA DE DTOs A LISTA DE ENTIDADES
    List<Evento> toEntityList (List<EventoDTO> dtos);


    /* ==========================
     *   CONVERSION DE FECHAS
     * ========================== */

    //-----LOCALDATE -> STRING
    //---- ENTITY    -> DTO
    @Named("convertirFecha")
    default String convertirFechaAString(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }




    //-----LOCALDATE -> STRING
    //---- DTO    ->    ENTITY
    @Named("convertirFechaInversa")
    default LocalDateTime convertirFechaDesdeString(String fecha) {
        return fecha != null ? LocalDateTime.parse(fecha) : null;
    }


}

package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.ModificarEventoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.servicios.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private  EventoService eventoService;



    /*----------------------------------------------------------------*/
    //MUESTRA EVENTO SEGUN ID
    @GetMapping("/{id}")
    public EventoDTO listarEventosPorId(@Valid @PathVariable Integer id){
        return eventoService.obtenerPorId(id);
    }
    /*----------------------------------------------------------------*/


    /*----------------------------------------------------------------*/
    //MUESTRA CON FILTROS O MUESTRA TODOS SI NO SE SELECCIONA FILTRO
    /*----------------------------------------------------------------*/

    @GetMapping("/filtrar")
    public List<EventoDTO> filtrarEventos(
            @Valid
            @RequestParam (required = false) String lugar,
            @RequestParam (required = false) String fecha)
    {
        LocalDate fechaConvertida = null;
        //SI MANDAMOS UNA FECHA LA CONVERTIMOS

        if (fecha != null && !fecha.trim().isEmpty()){
            fecha = fecha.trim(); //COMO NO ELIMINEMOS LOS ESPACIOS, DA PETE!!!
            fechaConvertida = LocalDate.parse(fecha);
        }

        List<EventoDTO> eventos = eventoService.obtenerConFiltros(lugar, fechaConvertida);
        if (eventos.isEmpty()){
            throw new ElementoNoEncontradoException("No hay eventos con la fecha o lugar seleccionados");
        }

        return eventoService.obtenerConFiltros(lugar, fechaConvertida);
    }



    /*----------------------------------------------------------------*/
    //CREAR EVENTO
    /*----------------------------------------------------------------*/
    @PostMapping("/crear")
    public EventoDTO crearEvento(@Valid @RequestBody CrearEventoDTO dto){
        return eventoService.crearEvento(dto);
    }




    /*----------------------------------------------------------------*/
    //MODIFICAR EVENTO
    /*----------------------------------------------------------------*/

    @PutMapping("/{id}")
    public EventoDTO modificarEvento (@PathVariable Integer id, @Valid @RequestBody ModificarEventoDTO dto){

        return eventoService.modificarEvento(id, dto);
    }

    /*----------------------------------------------------------------*/
    //ELIMINAR EVENTO
    /*----------------------------------------------------------------*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@Valid @PathVariable Integer id){
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }

    /*----------------------------------------------------------------*/
    //MOSTRAR TODOS
    /*----------------------------------------------------------------*/
    @GetMapping
    public List<EventoDTO> mostrarTodos(){
        return eventoService.mostrarTodos();
    }

    //EVENTOS DESTACADOS
    @GetMapping("/destacados")
    public List<EventoDTO> obtenerEventosDestacados() {
        return eventoService.obtenerEventosDestacados();
    }


}














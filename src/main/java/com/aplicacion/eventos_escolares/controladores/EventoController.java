package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.ModificarEventoDTO;
import com.aplicacion.eventos_escolares.servicios.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private  EventoService eventoService;



    /*----------------------------------------------------------------*/
    //MUESTRA EVENTO SEGUN ID
    @GetMapping("/{id}")
    public EventoDTO listarEventosPorId(@PathVariable Integer id){
        return eventoService.obtenerPorId(id);
    }
    /*----------------------------------------------------------------*/


    /*----------------------------------------------------------------*/
    //MUESTRA CON FILTROS O MUESTRA TODOS SI NO SE SELECCIONA FILTRO
    /*----------------------------------------------------------------*/

    @GetMapping("/filtrar")
    public List<EventoDTO> filtrarEventos(
            @RequestParam (required = false) String lugar,
            @RequestParam (required = false) String fecha)
    {
        LocalDate fechaConvertida = null;
        //SI MANDAMOS UNA FECHA LA CONVERTIMOS

        if (fecha != null && !fecha.trim().isEmpty()){
            fecha = fecha.trim(); //COMO NO ELIMINEMOS LOS ESPACIOS, DA PETE!!!
            fechaConvertida = LocalDate.parse(fecha);
        }

        return eventoService.obtenerConFiltros(lugar, fechaConvertida);
    }



    /*----------------------------------------------------------------*/
    //CREAR EVENTO
    /*----------------------------------------------------------------*/
    @PostMapping("/crear")
    public EventoDTO crearEvento(@RequestBody CrearEventoDTO dto){
        return eventoService.crearEvento(dto);
    }




    /*----------------------------------------------------------------*/
    //MODIFICAR EVENTO
    /*----------------------------------------------------------------*/

    @PutMapping("/{id}")
    public EventoDTO modificarEvento (@PathVariable Integer id, @RequestBody ModificarEventoDTO dto){

        return eventoService.modificarEvento(id, dto);
    }

}














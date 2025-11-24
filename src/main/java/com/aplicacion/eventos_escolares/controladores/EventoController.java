package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.EventoDTO;
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




}













/*
    @PostMapping
    public ResponseEntity<Evento> crear(@RequestBody EventoDTO dto){
        Evento eventoGuardado = eventoService.guardarDesdeDTO(dto);
        return ResponseEntity.ok(eventoGuardado);
    }


    @GetMapping("/{id}")
    public Optional<Evento> obtenerPorId(@PathVariable Integer id) {
        return eventoService.buscarPorId(id);
    }



    @GetMapping
    public List<EventoDTO> listarDTO(){

        List<Eventos> eventos = eventosService.listarTodos(); //Obtenemos todos los eventos
        List<EventoDTO> dtos = new ArrayList<>(); //lista vacía para los dto

        //convertimos evento por evento
        for (Eventos e : eventos){
            EventoDTO dto = eventosService.convertirAEventoDetallesDTO(e);
            dtos.add(dto);
        }
        return dtos; //devolvemos la lista final
    }




    @GetMapping("/todos")
    public List<Evento> listarTodos(){
        return eventoService.listarTodos();
    }





    @PostMapping
    public Eventos crear(@RequestBody Eventos evento) {
        return eventosService.guardar(evento);
    }

    @PutMapping("/{id}")
    public Eventos actualizar(@PathVariable Integer id, @RequestBody Eventos evento) {
        evento.setId(id);
        return eventosService.guardar(evento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        eventosService.eliminar(id);
    }
 */














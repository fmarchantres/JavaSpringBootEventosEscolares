package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.CrearEventoDTO;
import com.aplicacion.eventos_escolares.dto.EventoDetallesDTO;
import com.aplicacion.eventos_escolares.modelos.Eventos;
import com.aplicacion.eventos_escolares.servicios.EventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventosController {

    @Autowired
    private EventosService eventosService;



    @PostMapping
    public ResponseEntity<Eventos> crear(@RequestBody CrearEventoDTO dto){
        Eventos eventoGuardado = eventosService.guardarDesdeDTO(dto);
        return ResponseEntity.ok(eventoGuardado);
    }


    @GetMapping("/{id}")
    public Optional<Eventos> obtenerPorId(@PathVariable Integer id) {
        return eventosService.buscarPorId(id);
    }

    @GetMapping
    public List<EventoDetallesDTO> listarDTO(){

        List<Eventos> eventos = eventosService.listarTodos(); //Obtenemos todos los eventos
        List<EventoDetallesDTO> dtos = new ArrayList<>(); //lista vacía para los dto

        //convertimos evento por evento
        for (Eventos e : eventos){
            EventoDetallesDTO dto = eventosService.convertirAEventoDetallesDTO(e);
            dtos.add(dto);
        }
        return dtos; //devolvemos la lista final
    }






















/*




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


}











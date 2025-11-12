package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Eventos;
import com.aplicacion.eventos_escolares.servicios.EventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventosController {

    @Autowired
    private EventosService eventosService;

    @GetMapping
    public List<Eventos> listar() {
        return eventosService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Eventos> obtenerPorId(@PathVariable Integer id) {
        return eventosService.buscarPorId(id);
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
}

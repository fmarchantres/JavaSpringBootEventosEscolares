package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Notificaciones;
import com.aplicacion.eventos_escolares.servicios.NotificacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    @GetMapping
    public List<Notificaciones> listar() {
        return notificacionesService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Notificaciones> obtenerPorId(@PathVariable Integer id) {
        return notificacionesService.buscarPorId(id);
    }

    @PostMapping
    public Notificaciones crear(@RequestBody Notificaciones notificacion) {
        return notificacionesService.guardar(notificacion);
    }

    @PutMapping("/{id}")
    public Notificaciones actualizar(@PathVariable Integer id, @RequestBody Notificaciones notificacion) {
        notificacion.setId(id);
        return notificacionesService.guardar(notificacion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        notificacionesService.eliminar(id);
    }
}

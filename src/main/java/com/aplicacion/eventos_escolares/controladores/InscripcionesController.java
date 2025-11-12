package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Inscripciones;
import com.aplicacion.eventos_escolares.servicios.InscripcionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionesController {

    @Autowired
    private InscripcionesService inscripcionesService;

    @GetMapping
    public List<Inscripciones> listar() {
        return inscripcionesService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Inscripciones> obtenerPorId(@PathVariable Integer id) {
        return inscripcionesService.buscarPorId(id);
    }

    @PostMapping
    public Inscripciones crear(@RequestBody Inscripciones inscripcion) {
        return inscripcionesService.guardar(inscripcion);
    }

    @PutMapping("/{id}")
    public Inscripciones actualizar(@PathVariable Integer id, @RequestBody Inscripciones inscripcion) {
        inscripcion.setId(id);
        return inscripcionesService.guardar(inscripcion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        inscripcionesService.eliminar(id);
    }
}

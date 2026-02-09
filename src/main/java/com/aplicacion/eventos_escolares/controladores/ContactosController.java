package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Contactos;
import com.aplicacion.eventos_escolares.servicios.ContactosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contactos")
public class ContactosController {

    @Autowired
    private ContactosService contactosService;

    @GetMapping
    public List<Contactos> listar() {
        return contactosService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Contactos> obtenerPorId(@PathVariable Integer id) {
        return contactosService.buscarPorId(id);
    }

    @PostMapping
    public Contactos crear(@RequestBody Contactos contacto) {
        return contactosService.guardar(contacto);
    }

    @PutMapping("/{id}")
    public Contactos actualizar(@PathVariable Integer id, @RequestBody Contactos contacto) {
        contacto.setId(id);
        return contactosService.guardar(contacto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        contactosService.eliminar(id);
    }
}

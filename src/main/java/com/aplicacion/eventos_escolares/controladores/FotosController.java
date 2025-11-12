package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Fotos;
import com.aplicacion.eventos_escolares.servicios.FotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fotos")
@CrossOrigin(origins = "*")
public class FotosController {

    @Autowired
    private FotosService fotosService;

    @GetMapping
    public List<Fotos> listar() {
        return fotosService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Fotos> obtenerPorId(@PathVariable Integer id) {
        return fotosService.buscarPorId(id);
    }

    @PostMapping
    public Fotos crear(@RequestBody Fotos foto) {
        return fotosService.guardar(foto);
    }

    @PutMapping("/{id}")
    public Fotos actualizar(@PathVariable Integer id, @RequestBody Fotos foto) {
        foto.setId(id);
        return fotosService.guardar(foto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        fotosService.eliminar(id);
    }
}

package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.converter.FotoMapper;
import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.modelos.Foto;
import com.aplicacion.eventos_escolares.servicios.FotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fotos")
public class FotoController {

    @Autowired
    private FotoService fotoService;
    @Autowired
    private FotoMapper fotoMapper;



    @PostMapping("/eventos/{id}/galeria")
    public FotoDTO subirFotoAGaleria(@PathVariable Integer id,@Valid @RequestBody FotoDTO dto) {
        return fotoService.subirFotoAGaleria(id, dto);
    }


    //CRUD
    @GetMapping
    public List<Foto> listar() {
        return fotoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Foto> obtenerPorId(@PathVariable Integer id) {
        return fotoService.buscarPorId(id);
    }

    @PostMapping
    public Foto crear(@RequestBody Foto foto) {
        return fotoService.guardar(foto);
    }

    @PutMapping("/{id}")
    public Foto actualizar(@PathVariable Integer id, @RequestBody Foto foto) {
        foto.setId(id);
        return fotoService.guardar(foto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        fotoService.eliminar(id);
    }





}

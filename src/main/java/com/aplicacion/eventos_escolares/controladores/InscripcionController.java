package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.converter.InscripcionMapper;
import com.aplicacion.eventos_escolares.dto.EstadisticasDTO;
import com.aplicacion.eventos_escolares.dto.InscripcionDTO;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import com.aplicacion.eventos_escolares.servicios.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionMapper inscripcionMapper;

    @PostMapping("/eventos/{id}")
    public InscripcionDTO registrarUsuario (@PathVariable Integer id, @RequestBody InscripcionDTO dto) {

        Inscripcion guardada = inscripcionService.registrarUsuario(id,dto);
        return inscripcionMapper.toDTO(guardada);
    }

    @GetMapping("/estadisticas")
    public List<EstadisticasDTO> obtenerEstadisticasPorId (){
        return inscripcionService.obtenerEstadisticas();
    }

    @GetMapping("/por_usuario/{id}")
    public List<InscripcionDTO> buscarPorId (@PathVariable Integer id) {
        List<Inscripcion> guardada = inscripcionService.buscarPorUsuario(id);
        List<InscripcionDTO> lista = new ArrayList<>();
        for(Inscripcion inscripcion : guardada){
            lista.add(inscripcionMapper.toDTO(inscripcion));
        }
        return lista;
    }


    //CRUD
    @GetMapping
    public List<Inscripcion> listar() {
        return inscripcionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Inscripcion> obtenerPorId(@PathVariable Integer id) {
        return inscripcionService.buscarPorId(id);
    }

    @PostMapping
    public Inscripcion crear(@RequestBody Inscripcion inscripcion) {
        return inscripcionService.guardar(inscripcion);
    }

    @PutMapping("/{id}")
    public Inscripcion actualizar(@PathVariable Integer id, @RequestBody Inscripcion inscripcion) {
        inscripcion.setId(id);
        return inscripcionService.guardar(inscripcion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        inscripcionService.eliminar(id);
    }
}

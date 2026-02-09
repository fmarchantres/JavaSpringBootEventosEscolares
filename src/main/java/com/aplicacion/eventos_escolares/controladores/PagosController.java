package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Pagos;
import com.aplicacion.eventos_escolares.servicios.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
public class PagosController {

    @Autowired
    private PagosService pagosService;

    @GetMapping
    public List<Pagos> listar() {
        return pagosService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Pagos> obtenerPorId(@PathVariable Integer id) {
        return pagosService.buscarPorId(id);
    }

    @PostMapping
    public Pagos crear(@RequestBody Pagos pago) {
        return pagosService.guardar(pago);
    }

    @PutMapping("/{id}")
    public Pagos actualizar(@PathVariable Integer id, @RequestBody Pagos pago) {
        pago.setId(id);
        return pagosService.guardar(pago);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagosService.eliminar(id);
    }
}

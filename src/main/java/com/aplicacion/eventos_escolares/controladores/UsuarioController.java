package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioParticipaEventoDTO;

import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.servicios.InscripcionService;
import com.aplicacion.eventos_escolares.servicios.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private InscripcionService inscripcionService;


    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }


    @GetMapping("/estadisticas/usuarioActivo")
    public UsuarioEstadisticaDTO listarEstadisticaUsuarioActivo() {
        return usuarioService.registrarEstadisticaUsuario();
    }


    @PostMapping("/registrar")
    public Usuario registrar (@Valid @RequestBody RegistrarUsuarioDTO dto) {
        return usuarioService.registrarUsuario(dto);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        return usuarioService.login(usuario.getEmail(), usuario.getPassword());
    }


    @GetMapping ("/{id}/eventos")
    public List<UsuarioParticipaEventoDTO> obtenerEventosPorId (@PathVariable Integer id) {
        return inscripcionService.obtenerEventosPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }





}


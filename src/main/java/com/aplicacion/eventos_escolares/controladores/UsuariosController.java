package com.aplicacion.eventos_escolares.controladores;


import com.aplicacion.eventos_escolares.modelos.Usuarios;
import com.aplicacion.eventos_escolares.servicios.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // permite peticiones desde Angular
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public List<Usuarios> listar() {
        return usuariosService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuarios> obtenerPorId(@PathVariable Integer id) {
        return usuariosService.buscarPorId(id);
    }

    @PostMapping
    public Usuarios crear(@RequestBody Usuarios usuario) {
        return usuariosService.guardar(usuario);
    }

    @PutMapping("/{id}")
    public Usuarios actualizar(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        usuario.setId(id);
        return usuariosService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuariosService.eliminar(id);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario) {
        if (usuariosService.existeEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo electrónico ya está registrado.");
        }

        Usuarios nuevo = usuariosService.guardar(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios usuario) {
        Optional<Usuarios> usuarioEncontrado = usuariosService.buscarPorEmail(usuario.getEmail());

        if (usuarioEncontrado.isPresent() &&
                usuarioEncontrado.get().getPassword().equals(usuario.getPassword())) {

            return ResponseEntity.ok(usuarioEncontrado.get());
        }

        return ResponseEntity.status(401).body("Email o contraseña incorrectos");
    }



}


package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.modelos.Usuario;
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
public class UsuarioController {

    @Autowired
    private UsuariosService usuariosService;


    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuariosService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuariosService.buscarPorId(id);
    }

    /*
    @PostMapping
    public Usuarios crear(@RequestBody Usuarios usuario) {
        return usuariosService.guardar(usuario);
    }
    */

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuariosService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuariosService.eliminar(id);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuariosService.existeEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo electrónico ya está registrado.");
        }

        Usuario nuevo = usuariosService.guardar(usuario);
        return ResponseEntity.ok(nuevo);
    }

    /*
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios usuario) {
        Optional<UsuarioDTO> usuarioEncontrado = usuariosService.buscarPorEmail(usuario.getEmail());

        if (usuarioEncontrado.isPresent() &&
                usuarioEncontrado.get().getPassword().equals(usuario.getPassword())) {

            return ResponseEntity.ok(usuarioEncontrado.get());
        }

        return ResponseEntity.status(401).body("Email o contraseña incorrectos");
    }
    */

}


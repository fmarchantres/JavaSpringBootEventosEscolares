package com.aplicacion.eventos_escolares.controladores;

import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.servicios.InscripcionService;
import com.aplicacion.eventos_escolares.servicios.UsuarioService;
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
    private UsuarioService usuarioService;
    @Autowired
    private InscripcionService inscripcionService;


    @GetMapping("/usuarios")
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    /*
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }
     */

    @PostMapping("/registrar")
    public Usuario registrar (@RequestBody RegistrarUsuarioDTO dto) {
        return usuarioService.registrarUsuario(dto);
    }


    @GetMapping ("/{id}/eventos")
    public List<Evento> obtenerEventosPorId (@PathVariable Integer id) {
        return inscripcionService.obtenerEventosPorId(id);
    }




    /*
    * Consultar todas las inscripciones del usuario
    De cada inscripción obtener inscripcion.getEvento()
    Meter todos esos eventos en una lista y devolverla
    /*

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        return usuarioService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioService.existeEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El correo electrónico ya está registrado.");
        }

        Usuario nuevo = usuarioService.guardar(usuario);
        return ResponseEntity.ok(nuevo);
    }


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


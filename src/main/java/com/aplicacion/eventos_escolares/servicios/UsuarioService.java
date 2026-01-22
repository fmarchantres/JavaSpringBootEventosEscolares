package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.EventoMapper;
import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.converter.UsuarioMapper;
import com.aplicacion.eventos_escolares.dto.EventoDTO;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Inscripcion;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final RegistrarUsuarioMapper  registrarUsuarioMapper;
    private final EventoMapper eventoMapper;


    public Usuario registrarUsuario(RegistrarUsuarioDTO dto) {

        //Comprobacion email
        if(usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ElementoNoEncontradoException("El email ya está registrado");
        }

        //Mapear DTO a Entidad
        Usuario usuario = registrarUsuarioMapper.toEntity(dto);

        // Asignar fecha de registro (no viene del cliente)
        usuario.setFechaRegistro(LocalDateTime.now());


        //Guardar usuario
        return usuarioRepository.save(usuario);
    }


    public UsuarioEstadisticaDTO registrarEstadisticaUsuario() {
        return usuarioRepository.findEstadisticaUsuario();
    }


    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /*
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    */



    public void eliminarUsuario(Integer id) {

        if (!usuarioRepository.existsById(id)) {
            throw new ElementoNoEncontradoException("Usuario no encontrado");
        }

        usuarioRepository.deleteById(id);
    }

    public Usuario login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ElementoNoEncontradoException("Email no registrado"));

        if (!usuario.getPassword().equals(password)) {
            throw new ElementoNoEncontradoException("Contraseña incorrecta");
        }

        return usuario;
    }

    //METODO PARA OBTENER USUARIOS QUE PARTICIPAN EN UN EVENTO
    @Transactional
    public List<EventoDTO> obtenerEventosUsuario(Integer usuarioId) {

        //Comprobar que el usuario existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Usuario no encontrado"));

        //Obtener las inscripciones del usuario
        List<Inscripcion> inscripciones = usuario.getInscripciones();

        //Si no participa en ningún evento
        if (inscripciones.isEmpty()) {
            throw new ElementoNoEncontradoException("El usuario no participa en ningún evento");
        }

        // 4. Extraer los eventos de las inscripciones y mapear a DTO
        return inscripciones.stream()
                .map(Inscripcion::getEvento)
                .map(eventoMapper::toDTO)
                .toList();
    }





}

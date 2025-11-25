package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.UsuarioMapper;
import com.aplicacion.eventos_escolares.dto.UsuarioDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;
    private UsuarioMapper usuarioMapper;

    public Usuario registrarUsuario(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    public boolean existeEmail(String email) {
        return usuariosRepository.findByEmail(email).isPresent();
    }

    public List<Usuario> listarTodos() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        return usuariosRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuariosRepository.deleteById(id);
    }

    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        Usuario user =  usuariosRepository.findByEmail(email)
                .orElse(null);
        return Optional.ofNullable(usuarioMapper.toDTO(user));
    }

}

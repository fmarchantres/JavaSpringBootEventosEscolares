package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Usuarios;
import com.aplicacion.eventos_escolares.repositories.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Usuarios registrarUsuario(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    public boolean existeEmail(String email) {
        return usuariosRepository.findByEmail(email).isPresent();
    }

    public List<Usuarios> listarTodos() {
        return usuariosRepository.findAll();
    }

    public Optional<Usuarios> buscarPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    public Usuarios guardar(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    public void eliminar(Integer id) {
        usuariosRepository.deleteById(id);
    }

    public Optional<Usuarios> buscarPorEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

}

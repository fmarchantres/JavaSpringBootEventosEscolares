package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.converter.UsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
import com.aplicacion.eventos_escolares.dto.UsuarioEstadisticaDTO;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final RegistrarUsuarioMapper  registrarUsuarioMapper;

    public Usuario registrarUsuario(RegistrarUsuarioDTO dto) {

        //Mapear DTO a Entidad
        Usuario usuario = registrarUsuarioMapper.toEntity(dto);

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

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);}
}

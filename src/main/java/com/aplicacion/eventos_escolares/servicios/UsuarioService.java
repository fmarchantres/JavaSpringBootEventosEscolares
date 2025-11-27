package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.RegistrarUsuarioMapper;
import com.aplicacion.eventos_escolares.converter.UsuarioMapper;
import com.aplicacion.eventos_escolares.dto.RegistrarUsuarioDTO;
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

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }


    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    /*
    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }







    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        Usuario user =  usuarioRepository.findByEmail(email)
                .orElse(null);
        return Optional.ofNullable(usuarioMapper.toDTO(user));
    }

     */

}

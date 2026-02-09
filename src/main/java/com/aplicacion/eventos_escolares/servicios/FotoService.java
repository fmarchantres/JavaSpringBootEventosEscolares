package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.FotoMapper;
import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.exception.ElementoNoEncontradoException;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Foto;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.EventoRepository;
import com.aplicacion.eventos_escolares.repositories.FotoRepository;
import com.aplicacion.eventos_escolares.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private FotoMapper fotoMapper;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public FotoDTO subirFotoAGaleria(Integer eventoId, FotoDTO dto) {
        // 1. Buscar evento
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ElementoNoEncontradoException("Evento no encontrado con ID: " + eventoId));

        // 2. Buscar usuario (con lógica de autorescate)
        // Si el usuarioId del DTO no existe, buscamos el ID 1 o creamos uno genérico
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseGet(() -> {
                    System.out.println("Usuario no encontrado, intentando recuperar usuario por defecto...");
                    return usuarioRepository.findById(1).orElseGet(() -> {
                        System.out.println("Creando usuario por defecto para evitar error en el móvil");
                        Usuario nuevo = new Usuario();
                        nuevo.setNombre("Usuario Movil");
                        nuevo.setEmail("movil@evento.com");
                        // Asigna aquí otros campos obligatorios que tenga tu entidad Usuario
                        return usuarioRepository.save(nuevo);
                    });
                });

        // 3. Convertir DTO a Entity
        Foto foto = fotoMapper.toEntity(dto);

        // 4. Asignar relaciones
        foto.setUsuario(usuario);
        foto.setEvento(evento);

        // 5. Guardar entidad
        Foto guardada = fotoRepository.save(foto);
        System.out.println("Foto guardada exitosamente en la DB: " + guardada.getUrl());

        // 6. Retornar DTO
        return fotoMapper.toDTO(guardada);
    }

    // --- CRUD ESTÁNDAR ---

    public List<Foto> listarTodas() {
        return fotoRepository.findAll();
    }

    public Optional<Foto> buscarPorId(Integer id) {
        return fotoRepository.findById(id);
    }

    @Transactional
    public Foto guardar(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Transactional
    public void eliminar(Integer id) {
        fotoRepository.deleteById(id);
    }
}
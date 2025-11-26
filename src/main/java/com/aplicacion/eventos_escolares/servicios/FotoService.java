package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.converter.FotoMapper;
import com.aplicacion.eventos_escolares.dto.FotoDTO;
import com.aplicacion.eventos_escolares.modelos.Evento;
import com.aplicacion.eventos_escolares.modelos.Foto;
import com.aplicacion.eventos_escolares.modelos.Usuario;
import com.aplicacion.eventos_escolares.repositories.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FotoService {

    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private FotoMapper fotoMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EventoService eventoService;


    //CRUD
    public List<Foto> listarTodas(){
        return fotoRepository.findAll();
    }
    public Optional<Foto> buscarPorId(Integer id){
        return fotoRepository.findById(id);
    }
    public Foto guardar(Foto foto){
        return fotoRepository.save(foto);
    }
    public void eliminar (Integer id){
        fotoRepository.deleteById(id);
    }


    public Foto subirFotoAGaleria (Integer eventoId, FotoDTO dto){
        //Buscar evento
        Evento evento = eventoService.buscarPorId(eventoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Evento no encontrado"));

        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));


        //Convertir DTO a Entity
        Foto foto = fotoMapper.toEntity(dto);

        //Asignar relaciones
        foto.setUsuario(usuario);
        foto.setEvento(evento);

        //Guardar en la BD
        return fotoRepository.save(foto);
    }



}

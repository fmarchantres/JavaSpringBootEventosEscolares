package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Fotos;
import com.aplicacion.eventos_escolares.repositories.FotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotosService {
    @Autowired
    private FotosRepository fotosRepository;

    public List<Fotos> listarTodas(){
        return fotosRepository.findAll();
    }

    public Optional<Fotos> buscarPorId(Integer id){
        return fotosRepository.findById(id);
    }

    public Fotos guardar(Fotos foto){
        return fotosRepository.save(foto);
    }

    public void eliminar (Integer id){
        fotosRepository.deleteById(id);
    }
}

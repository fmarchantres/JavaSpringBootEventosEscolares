package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Pagos;
import com.aplicacion.eventos_escolares.repositories.PagosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagosService {
    @Autowired
    private PagosRepository pagosRepository;

    public List<Pagos> listarTodos(){
        return pagosRepository.findAll();
    }

    public Optional<Pagos> buscarPorId(Integer id){
        return pagosRepository.findById(id);
    }
    public Pagos guardar(Pagos pagos){
        return pagosRepository.save(pagos);
    }

    public void eliminar(Integer id){
        pagosRepository.deleteById(id);
    }

}

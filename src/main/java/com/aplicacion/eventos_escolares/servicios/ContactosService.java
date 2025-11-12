package com.aplicacion.eventos_escolares.servicios;

import com.aplicacion.eventos_escolares.modelos.Contactos;
import com.aplicacion.eventos_escolares.repositories.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactosService {

    @Autowired
    private ContactoRepository contactoRepository;

    public List<Contactos> listarTodos() {
        return contactoRepository.findAll();
    }

    public Optional<Contactos> buscarPorId(Integer id) {
        return contactoRepository.findById(id);
    }

    public Contactos guardar(Contactos contacto) {
        return contactoRepository.save(contacto);
    }

    public void eliminar(Integer id) {
        contactoRepository.deleteById(id);
    }
}

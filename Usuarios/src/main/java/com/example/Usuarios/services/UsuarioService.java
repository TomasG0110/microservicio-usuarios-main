package com.example.Usuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> findAll(){
        return usuarioRepository.findAll();
    }
    public Usuarios findById(long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuarios save(Usuarios usuarios){
        return usuarioRepository.save(usuarios);
    }

    public void delete (Long id){
         usuarioRepository.deleteById(id);
    }

    public List<Usuarios> guardarUsuarios(List<Usuarios> usuarios){
        return usuarioRepository.saveAll(usuarios);
        
    }

}

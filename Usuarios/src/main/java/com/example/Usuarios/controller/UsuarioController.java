package com.example.Usuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.services.UsuarioService;


import java.util.List;


@RestController
    @RequestMapping("/ap1/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<List<Usuarios>> listar() {
        List<Usuarios> usuarios  = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
            //alternativa 2 -> return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(usuarios);
        //alternativa 2 -> return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuarios> guardar(@RequestBody Usuarios usuarios) {
        Usuarios usuarioMuevo = usuarioService.save(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMuevo);
    //    return new ResponseEntity<>(productoMuevo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscar(@PathVariable Integer id) {
        try {
            Usuarios usuarios = usuarioService.findById(id);
            return ResponseEntity.ok(usuarios);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> actualizar(@PathVariable Integer id, @RequestBody Usuarios usuarios) {
        try {
            Usuarios usu = usuarioService.findById(id);
            usu.setId(id);
            usu.setRun(usuarios.getRun());
            usu.setNombre(usuarios.getNombre());
            usu.setApellido(usuarios.getApellido());
            usu.setEmail(usuarios.getEmail());
            usu.setTelefono(usuarios.getTelefono());

            usuarioService.save(usu);
            return ResponseEntity.ok(usuarios);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Usuarios>> guardarUsuarios(@RequestBody List<Usuarios> usuarios){
        List<Usuarios> guardados = usuarioService.guardarUsuarios(usuarios);
        return ResponseEntity.ok(guardados);
    }
}

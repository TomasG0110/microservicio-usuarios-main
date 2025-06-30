package com.example.Usuarios.controller;

import com.example.Usuarios.Assemblers.UsuarioModelAssembler;
import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//http://localhost:8080/api/v2/usuarios
@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuarios>> getAllUsuarios() {
        List<EntityModel<Usuarios>> usuarios = usuarioService.findAll().stream()
                .map(usuarioModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Usuarios> getUsuarioById(@PathVariable Integer id) {
        Usuarios usuario = usuarioService.findById(id);
        if (usuario == null) {
            return null; 
        }
        return usuarioModelAssembler.toModel(usuario);
    }

    @PostMapping(produces =  MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuarios>> createUsuario(@RequestBody Usuarios usuario) {
        Usuarios createdUsuario = usuarioService.save(usuario);
        return ResponseEntity
                .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuarioById(createdUsuario.getId())).toUri())
                .body(usuarioModelAssembler.toModel(createdUsuario));
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuarios>> updateUsuario(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        Usuarios updatedUsuario = usuarioService.save(usuario);
        if (updatedUsuario == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuario con ID " + id + " no encontrado");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioModelAssembler.toModel(updatedUsuario));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer id) {
        Usuarios usuario = usuarioService.findById(id);
        if (usuario == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuario con ID " + id + " no encontrado");
            return ResponseEntity.notFound().build();
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

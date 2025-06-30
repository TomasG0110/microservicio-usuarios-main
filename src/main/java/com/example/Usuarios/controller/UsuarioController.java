package com.example.Usuarios.controller;

import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @Operation(summary = "Listar Usuarios", description = "Obtiene una lista de todos los usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "204", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Usuarios>> listar() {
        List<Usuarios> usuarios  = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(usuarios);
    }


    @PostMapping
    @Operation(summary = "Guardar Usuario", description = "Crea un nuevo usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<Usuarios> guardar(@RequestBody Usuarios usuarios) {
        Usuarios usuarioMuevo = usuarioService.save(usuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMuevo);
    //    return new ResponseEntity<>(productoMuevo, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuario por ID", description = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuarios> buscar(@PathVariable Integer id) {
        try {
            Usuarios usuarios = usuarioService.findById(id);
            return ResponseEntity.ok(usuarios);
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Usuario", description = "Actualiza un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
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
    @Operation(summary = "Eliminar Usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Guardar múltiples Usuarios", description = "Crea una lista de nuevos usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuarios creados exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuarios.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    public ResponseEntity<List<Usuarios>> guardarUsuarios(@RequestBody List<Usuarios> usuarios){
        List<Usuarios> guardados = usuarioService.guardarUsuarios(usuarios);
        return ResponseEntity.ok(guardados);
    }
}

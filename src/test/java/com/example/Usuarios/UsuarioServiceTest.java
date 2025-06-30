package com.example.Usuarios;

import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.repository.UsuarioRepository;
import com.example.Usuarios.services.UsuarioService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;
    
    @MockBean
    private UsuarioRepository usuarioRepository;
    

    @Test
    public void testFindAll() {
        Mockito.when(usuarioRepository.findAll()).thenReturn(List.of(new Usuarios(1, "12345678-9", "John", "Doe",  "john.doe@email.com", "123456789")));
        List<Usuarios> usuarios = this.usuarioService.findAll();
        Assertions.assertNotNull(usuarios);
        Assertions.assertEquals(1,usuarios.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Usuarios usuario = new Usuarios(id, "12345678-9", "John", "Doe",  "john.doe@email.com", "123456789" );
        Mockito.when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Usuarios found = this.usuarioService.findById(id);
        Assertions.assertNotNull(found);
        Assertions.assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        Usuarios usuario = new Usuarios(1,"12345678-9", "John", "Doe",  "john.doe@email.com", "123456789");
        Mockito.when((Usuarios)this.usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuarios saved = this.usuarioService.save(usuario);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(1, saved.getId());
    }

    @Test
    public void testDelete() {
        int id = 1;

        doNothing().when(usuarioRepository).deleteById(id);
    
        usuarioService.delete(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
    
}


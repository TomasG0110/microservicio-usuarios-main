package com.example.Usuarios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import net.datafaker.Faker;

import com.example.Usuarios.model.Usuarios;
import com.example.Usuarios.repository.UsuarioRepository;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
    
       

       //Generar Usuarios
       for (int i = 0; i < 10; i++) {
        Usuarios usuarios = new Usuarios();
        //usuarios.setId(i+1);
        usuarios.setRun(faker.idNumber().valid());
        usuarios.setNombre(faker.name().firstName());
        usuarios.setApellido(faker.name().lastName());
        usuarios.setEmail(faker.internet().emailAddress());
        usuarios.setTelefono(faker.phoneNumber().cellPhone());

        usuarioRepository.save(usuarios);
        
       }
    }
}

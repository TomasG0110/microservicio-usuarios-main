package com.example.Usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuarios{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El valor del ID se generará automáticamente.
    private Integer id;

    @Column(unique = true, length = 13, nullable = false) // Define las restricciones para la columna en la tabla.
    private String run;

    @Column(nullable = false) // Esta columna no puede ser nula.
    private String nombre;

    @Column(nullable = false) // Esta columna no puede ser nula.
    private String apellido;

    @Column(nullable = false) // Esta columna no puede ser nula.
    private String email;
    
    @Column(nullable = false) // Esta columna no puede ser nula.
    private String telefono;


    
}

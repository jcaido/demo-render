package com.jcaido.demorender.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "propietarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Propietario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Column(name = "primer_apellido")
    private String primerApellido;
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    @Column(unique = true)
    private String dni;
    private String domicilio;
    @OneToOne()
    private CodigoPostal codigoPostal;
}

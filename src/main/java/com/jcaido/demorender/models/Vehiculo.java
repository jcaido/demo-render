package com.jcaido.demorender.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "vehiculos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Vehiculo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    @OneToOne()
    private Propietario propietario;
}

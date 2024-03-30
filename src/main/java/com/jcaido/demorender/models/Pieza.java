package com.jcaido.demorender.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "piezas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Pieza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "referencia", unique = true)
    private String referencia;
    @Column(name = "nombre_pieza")
    private String nombre;
    @Column(name = "precio_venta")
    private Double precio;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "pieza")
    private List<PiezasReparacion> piezasReparacion = new ArrayList<>();
}

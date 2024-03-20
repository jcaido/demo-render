package com.jcaido.demorender.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mano_de_obra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ManoDeObra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_nuevo_precio")
    private LocalDate fechaNuevoPrecio;
    @Column(name = "precio_hora")
    private Double precioHoraClienteTaller;
    @Column(name = "precio_hora_actual")
    private Boolean precioHoraClienteTallerActual;
}

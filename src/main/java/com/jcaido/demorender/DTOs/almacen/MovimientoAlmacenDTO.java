package com.jcaido.demorender.DTOs.almacen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovimientoAlmacenDTO {
    private String piezaReferencia;
    private String piezaNombre;
    private Long total;
    public record Movimiento(String piezaReferencia, String piezaNombre) {};
}

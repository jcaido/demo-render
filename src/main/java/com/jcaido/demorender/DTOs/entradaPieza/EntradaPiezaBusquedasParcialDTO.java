package com.jcaido.demorender.DTOs.entradaPieza;

import com.jcaido.demorender.models.Pieza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntradaPiezaBusquedasParcialDTO {
    private Long id;
    private Pieza pieza;
    private Integer cantidad;
    private Double precioEntrada;
}

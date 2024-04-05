package com.jcaido.demorender.DTOs.entradaPieza;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntradaPiezaCrearDTO {
    @NotNull(message = "debe introducir la cantidad")
    private Integer cantidad;
    @NotNull(message = "debe introducir el precio de la entrada")
    private Double precioEntrada;
}

package com.jcaido.demorender.DTOs.manoDeObra;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManoDeObraCrearDTO {

    @NotNull(message = "El precio hora de la mano de obra no puede ser nulo")
    private Double precioHoraClienteTaller;
}

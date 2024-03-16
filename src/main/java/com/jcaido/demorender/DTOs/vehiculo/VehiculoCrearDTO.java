package com.jcaido.demorender.DTOs.vehiculo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoCrearDTO {

    @NotBlank(message = "debe introducir la matricula")
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
}

package com.jcaido.demorender.DTOs.proveedor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProveedorDTO {
    private Long id;
    @NotBlank(message = "debe introducir el nombre")
    private String nombre;
    @NotBlank(message = "debe introducir el dni/CIF")
    private String dniCif;
    private String domicilio;
}

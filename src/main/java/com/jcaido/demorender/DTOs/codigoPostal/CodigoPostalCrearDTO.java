package com.jcaido.demorender.DTOs.codigoPostal;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CodigoPostalCrearDTO {
    @NotBlank(message =  "debe introducir el codigo")
    @Length(min=5, max=5, message = "el codigo postal debe tener 5 digitos")
    private String codigo;
    @NotBlank(message = "debe introducir una localidad")
    private String localidad;
    @NotBlank(message = "debe introducit una provincia")
    private String provincia;
}

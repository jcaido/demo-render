package com.jcaido.demorender.DTOs.codigoPostal;

import com.jcaido.demorender.models.CodigoPostal;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CodigoPostalDTO {
    private Long id;
    @NotBlank(message =  "debe introducir el codigo")
    @Length(min=5, max=5, message = "el codigo postal debe tener 5 digitos")
    private String codigo;
    @NotBlank(message = "debe introducir una localidad")
    private String localidad;
    @NotBlank(message = "debe introducit una provincia")
    private String provincia;

    public CodigoPostalDTO(CodigoPostal codigoPostal) {
        this.id = codigoPostal.getId();
        this.codigo = codigoPostal.getCodigo();
        this.localidad = codigoPostal.getLocalidad();
        this.provincia = codigoPostal.getProvincia();
    }
}

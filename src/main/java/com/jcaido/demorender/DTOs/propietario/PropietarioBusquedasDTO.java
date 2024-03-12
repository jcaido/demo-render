package com.jcaido.demorender.DTOs.propietario;

import com.jcaido.demorender.models.CodigoPostal;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropietarioBusquedasDTO {
    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
    private String domicilio;
    private CodigoPostal codigoPostal;
}

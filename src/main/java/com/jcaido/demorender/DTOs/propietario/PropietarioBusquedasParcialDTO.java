package com.jcaido.demorender.DTOs.propietario;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropietarioBusquedasParcialDTO {

    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
}

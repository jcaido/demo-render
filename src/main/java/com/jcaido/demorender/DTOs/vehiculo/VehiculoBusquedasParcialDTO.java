package com.jcaido.demorender.DTOs.vehiculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoBusquedasParcialDTO {

    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String propietarioDni;
}

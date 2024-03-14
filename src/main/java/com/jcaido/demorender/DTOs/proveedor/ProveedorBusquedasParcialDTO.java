package com.jcaido.demorender.DTOs.proveedor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProveedorBusquedasParcialDTO {
    private Long id;
    private String nombre;
    private String dniCif;
}

package com.jcaido.demorender.DTOs.proveedor;

import com.jcaido.demorender.models.CodigoPostal;
import com.jcaido.demorender.models.EntradaPieza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProveedorBusquedasDTO {
    private Long id;
    private String nombre;
    private String dniCif;
    private String domicilio;
    private CodigoPostal codigoPostal;
    private EntradaPieza entradaPieza;
}

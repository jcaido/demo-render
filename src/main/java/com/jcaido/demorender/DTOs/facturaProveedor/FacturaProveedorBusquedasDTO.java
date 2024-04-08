package com.jcaido.demorender.DTOs.facturaProveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.jcaido.demorender.models.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacturaProveedorBusquedasDTO {

    private Long id;
    private Proveedor proveedor;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaFactura;
    private String numeroFactura;
    private Integer tipoIVA;
    private Boolean contabilizada;
    private List<AlbaranProveedorBusquedasDTO> albaranesProveedores;
}

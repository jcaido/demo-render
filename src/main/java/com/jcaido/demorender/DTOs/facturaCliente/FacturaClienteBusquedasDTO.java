package com.jcaido.demorender.DTOs.facturaCliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jcaido.demorender.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturaClienteBusquedasDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaFactura;
    private String serie;
    private Long numeroFactura;
    private Integer tipoIVA;
    private OrdenReparacionBusquedasDTO ordenReparacion;
}

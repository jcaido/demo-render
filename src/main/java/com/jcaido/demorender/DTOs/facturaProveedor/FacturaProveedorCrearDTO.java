package com.jcaido.demorender.DTOs.facturaProveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacturaProveedorCrearDTO {

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha de la factura no puede ser nula")
    private LocalDate fechaFactura;
    private String numeroFactura;
    private Integer tipoIVA;
    private Boolean contabilizada = false;
}

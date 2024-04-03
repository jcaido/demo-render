package com.jcaido.demorender.DTOs.facturaCliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturaClienteCrearDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha de la factura no puede ser nula")
    private LocalDate fechaFactura;
    private Integer tipoIVA;
}

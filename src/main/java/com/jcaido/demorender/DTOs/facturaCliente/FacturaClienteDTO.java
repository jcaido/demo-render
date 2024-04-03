package com.jcaido.demorender.DTOs.facturaCliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jcaido.demorender.models.Propietario;
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
public class FacturaClienteDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha de la factura no puede ser nula")
    private LocalDate fechaFactura;
    private String serie;
    private Long numeroFactura;
    private Integer tipoIVA;
    private Propietario propietario;
}

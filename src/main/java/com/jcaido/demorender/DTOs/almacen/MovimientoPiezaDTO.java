package com.jcaido.demorender.DTOs.almacen;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovimientoPiezaDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaMovimiento;
    private String proveedorMatricula;
    private Integer cantidad;
}

package com.jcaido.demorender.DTOs.ordenReparacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
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
public class OrdenReparacionCrearDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha de apertura no puede ser nula")
    private LocalDate fechaApertura;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private Boolean cerrada;
    private Boolean facturada;
}

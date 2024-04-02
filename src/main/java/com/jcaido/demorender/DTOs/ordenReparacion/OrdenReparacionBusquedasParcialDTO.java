package com.jcaido.demorender.DTOs.ordenReparacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdenReparacionBusquedasParcialDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaApertura;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private String vehiculoMatricula;
    private String vehiculoMarca;
    private String vehiculoModelo;
}

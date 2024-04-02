package com.jcaido.demorender.DTOs.ordenReparacion;

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
public class OrdenReparacionReducidaDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCierre;
    private String vehiculoMatricula;
    private String vehiculoMarca;
    private String vehiculoModelo;
    private String descripcion;
}

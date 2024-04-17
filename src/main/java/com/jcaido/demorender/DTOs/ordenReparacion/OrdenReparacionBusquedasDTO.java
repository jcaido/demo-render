package com.jcaido.demorender.DTOs.ordenReparacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.jcaido.demorender.models.ManoDeObra;
import com.jcaido.demorender.models.Vehiculo;
import jakarta.validation.constraints.NotBlank;
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
public class OrdenReparacionBusquedasDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaApertura;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCierre;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private Double horas;
    private Boolean cerrada;
    private Boolean facturada;
    private Vehiculo vehiculo;
    private ManoDeObra manoDeObra;
}

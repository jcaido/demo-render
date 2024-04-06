package com.jcaido.demorender.DTOs.albaranProveedor;

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
public class AlbaranProveedorBusquedasParcialDTO {

    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private String proveedorNombre;
    private String proveedorDniCif;
}

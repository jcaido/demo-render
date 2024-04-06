package com.jcaido.demorender.DTOs.albaranProveedor;

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
public class AlbaranProveedorCrearDTO {

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha del albarán no puede ser nula")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private Boolean facturado = false;
}

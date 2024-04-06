package com.jcaido.demorender.DTOs.albaranProveedor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaBusquedasParcialDTO;
import com.jcaido.demorender.models.FacturaProveedor;
import com.jcaido.demorender.models.Proveedor;
import jakarta.validation.constraints.NotNull;
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
public class AlbaranProveedorBusquedasDTO {

    private Long id;
    private Proveedor proveedor;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha del albarán no puede ser nula")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private Boolean facturado = false;
    private List<EntradaPiezaBusquedasParcialDTO> entradasPiezas;
    private FacturaProveedor facturaProveedor;
}

package com.jcaido.demorender.services.facturaProveedor;

import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorDTO;

import java.time.LocalDate;
import java.util.List;

public interface FacturaProveedorService {

    FacturaProveedorDTO crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor);
    List<FacturaProveedorBusquedasDTO> findAll();
    FacturaProveedorBusquedasDTO findById(Long id);
    List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedoresEntreFechas(LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal);
    List<FacturaProveedorBusquedasDTO> obtenerFacturasPorProveedorEntreFechas(Long idProveedor, LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal);
    FacturaProveedorDTO modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor);
    String deleteById(Long id);
}

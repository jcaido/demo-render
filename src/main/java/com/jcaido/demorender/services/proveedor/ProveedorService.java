package com.jcaido.demorender.services.proveedor;

import com.jcaido.demorender.DTOs.proveedor.ProveedorBusquedasDTO;
import com.jcaido.demorender.DTOs.proveedor.ProveedorBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.proveedor.ProveedorCrearDTO;
import com.jcaido.demorender.DTOs.proveedor.ProveedorDTO;

import java.util.List;

public interface ProveedorService {

    ProveedorDTO crearProveedor(ProveedorCrearDTO proveedorCrearDTO, Long idCodigoPostal);
    List<ProveedorBusquedasDTO> findAll();
    List<ProveedorBusquedasParcialDTO> findAllParcial();
    ProveedorBusquedasDTO findById(Long id);
    ProveedorBusquedasDTO findByDniCif(String dniCif);
    List<ProveedorBusquedasDTO> findByNombre(String nombre);
    List<ProveedorBusquedasParcialDTO> findByNombreParcial(String nombre);
    ProveedorDTO modificarProveedor(ProveedorDTO proveedorDTO, Long idCodigoPostal);
    String deleteById(Long  id);
}

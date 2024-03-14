package com.jcaido.demorender.services.proveedor;

import com.jcaido.demorender.DTOs.proveedor.ProveedorDTO;
import com.jcaido.demorender.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorValidacionesUniqueService {

    private final ProveedorDTO proveedorDTO;
    private final ProveedorRepository proveedorRepository;
    private final ProveedorModificacionCambiosService proveedorModificacionCambiosService;

    public ProveedorValidacionesUniqueService(ProveedorDTO proveedorDTO, ProveedorRepository proveedorRepository, ProveedorModificacionCambiosService proveedorModificacionCambiosService) {
        this.proveedorDTO = proveedorDTO;
        this.proveedorRepository = proveedorRepository;
        this.proveedorModificacionCambiosService = proveedorModificacionCambiosService;
    }

    public boolean validacionUniqueDniCif(ProveedorDTO proveedorDTO) {

        if (proveedorModificacionCambiosService.isDniCifHaCambiado(proveedorDTO) && proveedorRepository.existsByDniCif(proveedorDTO.getDniCif()))
            return false;

        return true;
    }
}

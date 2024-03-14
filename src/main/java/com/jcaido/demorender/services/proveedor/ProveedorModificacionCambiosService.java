package com.jcaido.demorender.services.proveedor;

import com.jcaido.demorender.DTOs.proveedor.ProveedorDTO;
import com.jcaido.demorender.models.CodigoPostal;
import com.jcaido.demorender.repositories.CodigoPostalRepository;
import com.jcaido.demorender.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProveedorModificacionCambiosService {

    private final ProveedorDTO proveedorDTO;
    private final ProveedorRepository proveedorRepository;
    private final CodigoPostalRepository codigoPostalRepository;

    public ProveedorModificacionCambiosService(ProveedorDTO proveedorDTO, ProveedorRepository proveedorRepository, CodigoPostalRepository codigoPostalRepository) {
        this.proveedorDTO = proveedorDTO;
        this.proveedorRepository = proveedorRepository;
        this.codigoPostalRepository = codigoPostalRepository;
    }

    public boolean isDniCifHaCambiado(ProveedorDTO proveedorDTO) {

        String dniCIF = proveedorRepository.findById(proveedorDTO.getId()).get().getDniCif();

        if (dniCIF.equals(proveedorDTO.getDniCif()))
            return false;

        return true;
    }

    public boolean validacionCodigoPostal(Long idCodigoPostal) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(idCodigoPostal);

        if (codigoPostal.isPresent())
            return true;

        return false;
    }
}

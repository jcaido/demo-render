package com.jcaido.demorender.services.propietario;

import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
import com.jcaido.demorender.models.CodigoPostal;
import com.jcaido.demorender.repositories.CodigoPostalRepository;
import com.jcaido.demorender.repositories.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropietarioModificacionCambiosService {

    private final PropietarioDTO propietarioDTO;
    private final PropietarioRepository propietarioRepository;
    private final CodigoPostalRepository codigoPostalRepository;

    public PropietarioModificacionCambiosService(PropietarioDTO propietarioDTO, PropietarioRepository propietarioRepository, CodigoPostalRepository codigoPostalRepository) {
        this.propietarioDTO = propietarioDTO;
        this.propietarioRepository = propietarioRepository;
        this.codigoPostalRepository = codigoPostalRepository;
    }

    public boolean isDniHaCambiado(PropietarioDTO propietarioDTO) {

        String dni = propietarioRepository.findById(propietarioDTO.getId()).get().getDni();

        if (dni.equals(propietarioDTO.getDni()))
            return false;

        return true;
    }

    public boolean validacionCodigoPostal(Long id_codigoPostal) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(id_codigoPostal);

        if (codigoPostal.isPresent())
            return true;

        return false;
    }
}

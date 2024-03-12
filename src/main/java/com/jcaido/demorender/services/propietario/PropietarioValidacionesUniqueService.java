package com.jcaido.demorender.services.propietario;

import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
import com.jcaido.demorender.repositories.PropietarioRepository;
import org.springframework.stereotype.Service;

@Service
public class PropietarioValidacionesUniqueService {

    private final PropietarioDTO propietarioDTO;
    private final PropietarioRepository propietarioRepository;
    private final PropietarioModificacionCambiosService propietarioModificacionCambiosService;

    public PropietarioValidacionesUniqueService(PropietarioDTO propietarioDTO, PropietarioRepository propietarioRepository, PropietarioModificacionCambiosService propietarioModificacionCambiosService) {
        this.propietarioDTO = propietarioDTO;
        this.propietarioRepository = propietarioRepository;
        this.propietarioModificacionCambiosService = propietarioModificacionCambiosService;
    }

    public boolean validacionUniqueDni(PropietarioDTO propietarioDTO) {

        if (propietarioModificacionCambiosService.isDniHaCambiado(propietarioDTO) && propietarioRepository.existsByDni(propietarioDTO.getDni()))
            return false;

        return true;
    }
}

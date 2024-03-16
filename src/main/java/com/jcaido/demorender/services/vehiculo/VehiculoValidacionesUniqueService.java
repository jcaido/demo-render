package com.jcaido.demorender.services.vehiculo;

import com.jcaido.demorender.DTOs.vehiculo.VehiculoDTO;
import com.jcaido.demorender.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

@Service
public class VehiculoValidacionesUniqueService {

    private final VehiculoDTO vehiculoDTO;
    private final VehiculoRepository vehiculoRepository;
    private final VehiculoModificacionCambiosService vehiculoModificacionCambiosService;

    public VehiculoValidacionesUniqueService(VehiculoDTO vehiculoDTO, VehiculoRepository vehiculoRepository, VehiculoModificacionCambiosService vehiculoModificacionCambiosService) {
        this.vehiculoDTO = vehiculoDTO;
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoModificacionCambiosService = vehiculoModificacionCambiosService;
    }

    public boolean validacionUniqueMatricula(VehiculoDTO vehiculoDTO) {

        if (vehiculoModificacionCambiosService.isMatriculaHaCambiado(vehiculoDTO) && vehiculoRepository.existsByMatricula(vehiculoDTO.getMatricula()))
            return false;

        return true;
    }
}

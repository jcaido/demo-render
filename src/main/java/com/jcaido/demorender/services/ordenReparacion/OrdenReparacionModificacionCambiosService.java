package com.jcaido.demorender.services.ordenReparacion;

import com.jcaido.demorender.models.Vehiculo;
import com.jcaido.demorender.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdenReparacionModificacionCambiosService {

    private final VehiculoRepository vehiculoRepository;

    public OrdenReparacionModificacionCambiosService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public boolean validacionVehiculo(Long idVehiculo) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(idVehiculo);

        if (vehiculo.isPresent())
            return true;

        return false;
    }
}

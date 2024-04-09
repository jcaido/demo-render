package com.jcaido.demorender.services.vehiculo;

import com.jcaido.demorender.DTOs.vehiculo.VehiculoBusquedasDTO;
import com.jcaido.demorender.DTOs.vehiculo.VehiculoBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.vehiculo.VehiculoCrearDTO;
import com.jcaido.demorender.DTOs.vehiculo.VehiculoDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.Propietario;
import com.jcaido.demorender.models.Vehiculo;
import com.jcaido.demorender.repositories.PropietarioRepository;
import com.jcaido.demorender.repositories.VehiculoRepository;
import com.jcaido.demorender.services.ordenReparacion.OrdenReparacionService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final PropietarioRepository propietarioRepository;
    private final EntityManager entityManager;
    private final VehiculoValidacionesUniqueService vehiculoValidacionesUniqueService;
    private final VehiculoModificacionCambiosService vehiculoModificacionCambiosService;
    private final OrdenReparacionService ordenReparacionService;
    private final ModelMapper modelMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, PropietarioRepository propietarioRepository, EntityManager entityManager, VehiculoValidacionesUniqueService vehiculoValidacionesUniqueService, VehiculoModificacionCambiosService vehiculoModificacionCambiosService, OrdenReparacionService ordenReparacionService, ModelMapper modelMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.propietarioRepository = propietarioRepository;
        this.entityManager = entityManager;
        this.vehiculoValidacionesUniqueService = vehiculoValidacionesUniqueService;
        this.vehiculoModificacionCambiosService = vehiculoModificacionCambiosService;
        this.ordenReparacionService = ordenReparacionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public VehiculoDTO crearVehiculo(VehiculoCrearDTO vehiculoCrearDTO, Long id_propietario) {

        if (vehiculoRepository.existsByMatricula(vehiculoCrearDTO.getMatricula()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!vehiculoModificacionCambiosService.validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = modelMapper.map(vehiculoCrearDTO, Vehiculo.class);
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setPropietario(propietario);
        vehiculoRepository.save(vehiculo);

        VehiculoDTO vehiculoRespuesta = modelMapper.map(vehiculo, VehiculoDTO.class);

        return vehiculoRespuesta;
    }

    @Override
    public List<VehiculoBusquedasDTO> findAll() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> findAllPartial() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(vehiculo -> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public VehiculoBusquedasDTO findById(Long id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(id));

        VehiculoBusquedasDTO vehiculoEncontrado = modelMapper.map(vehiculo, VehiculoBusquedasDTO.class);

        return vehiculoEncontrado;
    }

    @Override
    public VehiculoBusquedasDTO findByMatricula(String matricula) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByMatricula(matricula);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "matricula", matricula);

        VehiculoBusquedasDTO vehiculoEncontrado = modelMapper.map(vehiculo, VehiculoBusquedasDTO.class);

        return vehiculoEncontrado;
    }

    @Override
    public List<VehiculoBusquedasDTO> findByMarca(String marca) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarca(marca);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasDTO> findByMarcaAndModelo(String marca, String modelo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(marca, modelo);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> findByMarcaModeloPartial(String marca, String modelo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(marca, modelo);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public String deleteById(Long id) {
        if (!vehiculoRepository.existsById(id))
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(id));

        if (ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen ordenes de reparacion relacionadas con ese vehiculo");

        vehiculoRepository.deleteById(id);

        String respuesta = "Vehiculo eliminado con exito";

        return respuesta;
    }

    @Override
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioSQL(Long id_propietario) {
        Query query = entityManager.createNativeQuery("SELECT * FROM vehiculos WHERE propietario_id = :id", Vehiculo.class);
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioHQL(Long id_propietario) {
        Query query = entityManager.createQuery("FROM Vehiculo v WHERE v.propietario.id = :id");
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorPropietarioHQLParcial(Long id_propietario) {
        Query query = entityManager.createQuery("FROM Vehiculo v WHERE v.propietario.id = :id");
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public VehiculoDTO modificarVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario) {

        if (vehiculoDTO.getId() == null)
            throw new BadRequestModificacionException("Vehiculo", "id");

        if (!vehiculoRepository.existsById(vehiculoDTO.getId()))
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(vehiculoDTO.getId()));

        if (!vehiculoValidacionesUniqueService.validacionUniqueMatricula(vehiculoDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!vehiculoModificacionCambiosService.validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoDTO.getId()).get();
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setMatricula(vehiculoDTO.getMatricula());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setPropietario(propietario);

        Vehiculo vehiculoModificado = vehiculoRepository.save(vehiculo);
        VehiculoDTO vehiculoModificadoDTO = modelMapper.map(vehiculo, VehiculoDTO.class);

        return vehiculoModificadoDTO;
    }
}

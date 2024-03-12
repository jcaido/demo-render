package com.jcaido.demorender.services.propietario;

import com.jcaido.demorender.DTOs.propietario.PropietarioBusquedasDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioCrearDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.CodigoPostal;
import com.jcaido.demorender.models.Propietario;
import com.jcaido.demorender.repositories.CodigoPostalRepository;
import com.jcaido.demorender.repositories.PropietarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    private final PropietarioRepository propietarioRepository;
    private final CodigoPostalRepository codigoPostalRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final PropietarioValidacionesUniqueService propietarioValidacionesUniqueService;
    private final PropietarioModificacionCambiosService propietarioModificacionCambiosService;

    public PropietarioServiceImpl(PropietarioRepository propietarioRepository, CodigoPostalRepository codigoPostalRepository, ModelMapper modelMapper, EntityManager entityManager, PropietarioValidacionesUniqueService propietarioValidacionesUniqueService, PropietarioModificacionCambiosService propietarioModificacionCambiosService) {
        this.propietarioRepository = propietarioRepository;
        this.codigoPostalRepository = codigoPostalRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.propietarioValidacionesUniqueService = propietarioValidacionesUniqueService;
        this.propietarioModificacionCambiosService = propietarioModificacionCambiosService;
    }

    @Override
    public PropietarioDTO crearPropietario(PropietarioCrearDTO propietarioCrearDTO, Long id_codigoPostal) {
        if (propietarioRepository.existsByDni(propietarioCrearDTO. getDni()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI del propietario ya existe");

        if (!propietarioModificacionCambiosService.validacionCodigoPostal(id_codigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo Postal asociado al propietario no existe");

        Propietario propietario = modelMapper.map(propietarioCrearDTO, Propietario.class);
        CodigoPostal codigoPostal = codigoPostalRepository.findById(id_codigoPostal).get();
        propietario.setCodigoPostal(codigoPostal);
        propietarioRepository.save(propietario);

        PropietarioDTO propietarioRespuesta = modelMapper.map(propietario, PropietarioDTO.class);

        return propietarioRespuesta;
    }

    @Override
    public List<PropietarioBusquedasDTO> findAll() {
        List<Propietario> propietarios = propietarioRepository.findAll();

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasParcialDTO> findAllPartial() {
        List<Propietario> propietarios = propietarioRepository.findAll();

        return propietarios.stream().map(propietario -> modelMapper.map(propietario, PropietarioBusquedasParcialDTO.class)).toList();
    }

    @Override
    public PropietarioBusquedasDTO findById(Long id) {
        Optional<Propietario> propietario = propietarioRepository.findById(id);

        if (!propietario.isPresent())
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(id));

        PropietarioBusquedasDTO propietarioEncontrado = modelMapper.map(propietario, PropietarioBusquedasDTO.class);

        return propietarioEncontrado;
    }

    @Override
    public PropietarioBusquedasDTO findByDni(String dni) {
        Optional<Propietario> propietario = propietarioRepository.findByDni(dni);

        if (!propietario.isPresent())
            throw new ResourceNotFoundException("Propietario", "dni", dni);

        PropietarioBusquedasDTO propietarioEncontrado = modelMapper.map(propietario, PropietarioBusquedasDTO.class);

        return propietarioEncontrado;
    }

    @Override
    public List<PropietarioBusquedasDTO> findByNombre(String nombre) {
        List<Propietario> propietarios = propietarioRepository.findByNombre(nombre);

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasDTO> findByPrimerApellido(String primerApellido) {
        List<Propietario> propietarios = propietarioRepository.findByPrimerApellido(primerApellido);

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasParcialDTO> findByPrimerApellidoPartial(String primerApellido) {
        List<Propietario> propietarios = propietarioRepository.findByPrimerApellido(primerApellido);

        return propietarios.stream().map(propietario -> modelMapper.map(propietario, PropietarioBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasDTO> findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido) {
        List<Propietario> propietarios = propietarioRepository.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, primerApellido, segundoApellido);

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasDTO> ObtenerPropietariosPorCodigoPostal(Long id) {
        Optional<CodigoPostal> codigo = codigoPostalRepository.findById(id);

        if (!codigo.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        List<Propietario> propietarios = codigo.get().getPropietarios();

        return propietarios.stream().map(propietario -> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasParcialDTO> obtenerPropietariosPorCodigoPostalParcial(Long id) {
        Optional<CodigoPostal> codigo = codigoPostalRepository.findById(id);

        if (!codigo.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        List<Propietario> propietarios = codigo.get().getPropietarios();

        return propietarios.stream().map(propietario -> modelMapper.map(propietario, PropietarioBusquedasParcialDTO.class)).toList();
    }

    @Override
    public String deleteById(Long id) {
        if (!propietarioRepository.existsById(id))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(id));

        //if (vehiculoService.obtenerVehiculosPorPropietarioHQL(id).size() > 0)
        //    throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen vehiculos relacionado con ese propietario");

        propietarioRepository.deleteById(id);

        String respuesta = "Propietario eliminado con exito";

        return respuesta;
    }

    @Override
    public List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostalSQL(Long id_codigoPostal) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM propietarios WHERE codigo_postal_id = :id", Propietario.class);
        query.setParameter("id", id_codigoPostal);
        List<Propietario> propietarios = query.getResultList();

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostalHQL(Long id_codigoPostal) {
        Query query = entityManager.createQuery("FROM Propietario p WHERE p.codigoPostal.id = :id" );
        query.setParameter("id", id_codigoPostal);
        List<Propietario> propietarios = query.getResultList();

        return propietarios.stream().map(propietario-> modelMapper.map(propietario, PropietarioBusquedasDTO.class)).toList();
    }

    @Override
    public PropietarioDTO modificarPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal) {
        if (propietarioDTO.getId() == null)
            throw new BadRequestModificacionException("Propietario", "id");

        if (!propietarioRepository.existsById(propietarioDTO.getId()))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(propietarioDTO.getId()));

        if (!propietarioValidacionesUniqueService.validacionUniqueDni(propietarioDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya existe");

        if (!propietarioModificacionCambiosService.validacionCodigoPostal(id_codigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo postal asociado al propietario no existe");

        Propietario propietario = propietarioRepository.findById(propietarioDTO.getId()).get();
        CodigoPostal codigoPostal = codigoPostalRepository.findById(id_codigoPostal).get();
        propietario.setNombre(propietarioDTO.getNombre());
        propietario.setPrimerApellido(propietarioDTO.getPrimerApellido());
        propietario.setSegundoApellido(propietarioDTO.getSegundoApellido());
        propietario.setDni(propietarioDTO.getDni());
        propietario.setDomicilio(propietarioDTO.getDomicilio());
        propietario.setCodigoPostal(codigoPostal);

        Propietario propietarioModificado = propietarioRepository.save(propietario);
        PropietarioDTO propietarioModificadoDTO = modelMapper.map(propietarioModificado, PropietarioDTO.class);

        return propietarioModificadoDTO;
    }
}

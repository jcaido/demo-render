package com.jcaido.demorender.services.codigoPostal;

import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.jcaido.demorender.DTOs.codigoPostal.CodigoPostalDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.CodigoPostal;
import com.jcaido.demorender.repositories.CodigoPostalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoPostalServiceImpl implements CodigoPostalService{

    private final CodigoPostalRepository codigoPostalRepository;
    private final CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService;
    private  final ModelMapper modelMapper;

    public CodigoPostalServiceImpl(CodigoPostalRepository codigoPostalRepository, CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService, ModelMapper modelMapper) {
        this.codigoPostalRepository = codigoPostalRepository;
        this.codigoPostalValidacionesUniqueService = codigoPostalValidacionesUniqueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CodigoPostalDTO crearCodigoPostal(CodigoPostalCrearDTO codigoPostalCrearDTO) {

        if (codigoPostalRepository.existsByCodigo(codigoPostalCrearDTO.getCodigo()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero del codigo postal ya existe");

        if (codigoPostalRepository.existsByLocalidad(codigoPostalCrearDTO.getLocalidad()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la localidad del codigo postal ya existe");

        CodigoPostal codigoPostal = modelMapper.map(codigoPostalCrearDTO, CodigoPostal.class);
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);
        CodigoPostalDTO codigoPostalRespuesta = modelMapper.map(nuevoCodigoPostal, CodigoPostalDTO.class);
        return codigoPostalRespuesta;
    }

    @Override
    public List<CodigoPostalDTO> findAll() {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findAll();

        return  codigosPostales.stream().map(codigoPostal-> modelMapper.map(codigoPostal, CodigoPostalDTO.class)).toList();
    }

    @Override
    public Page<CodigoPostalDTO> findAllPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CodigoPostal> codigosPostales = codigoPostalRepository.findAll(pageable);

        return codigosPostales.map(codigoPostal -> new CodigoPostalDTO(codigoPostal));
    }

    @Override
    public CodigoPostalDTO findById(Long id) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(id);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public CodigoPostalDTO findByCodigo(String codigo) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByCodigo(codigo);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "codigo", codigo);

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public List<CodigoPostalDTO> findByProvincia(String provincia) {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findByProvincia(provincia);

        return codigosPostales.stream().map(codigoPostal-> modelMapper.map(codigoPostal, CodigoPostalDTO.class)).toList();
    }

    @Override
    public CodigoPostalDTO findByLocalidad(String localidad) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByLocalidad(localidad);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "localidad", localidad);

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public String deleteById(Long id) {
        if (!codigoPostalRepository.existsById(id))
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        //if (propietarioService.obtenerPropietariosPorCodigoPostalHQL(id).size() > 0)
        //    throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen propietarios relacionados con ese codigo postal");

        codigoPostalRepository.deleteById(id);

        String respuesta = "Codigo Postal eliminado con exito";

        return respuesta;
    }

    @Override
    public CodigoPostalDTO modificarCodigoPostal(CodigoPostalDTO codigoPostalDTO) {
        if (codigoPostalDTO.getId() == null)
            throw new BadRequestModificacionException("Codigo Postal", "id");

        if (!codigoPostalRepository.existsById(codigoPostalDTO.getId()))
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(codigoPostalDTO.getId()));

        if (!codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La localidad ya existe");

        if (!codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo del Codigo Postal ya existe");

        CodigoPostal codigoPostal = codigoPostalRepository.findById(codigoPostalDTO.getId()).get();
        codigoPostal.setCodigo(codigoPostalDTO.getCodigo());
        codigoPostal.setLocalidad(codigoPostalDTO.getLocalidad());
        codigoPostal.setProvincia(codigoPostalDTO.getProvincia());

        CodigoPostal codigoPostalModificado = codigoPostalRepository.save(codigoPostal);
        CodigoPostalDTO codigoPostalModificadoDTO = modelMapper.map(codigoPostalModificado, CodigoPostalDTO.class);

        return codigoPostalModificadoDTO;
    }
}

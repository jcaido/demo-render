package com.jcaido.demorender.services.entradaPieza;

import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.jcaido.demorender.DTOs.entradaPieza.EntradaPiezaDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.AlbaranProveedor;
import com.jcaido.demorender.models.EntradaPieza;
import com.jcaido.demorender.models.Pieza;
import com.jcaido.demorender.repositories.AlbaranProveedorRepository;
import com.jcaido.demorender.repositories.EntradaPiezaRepository;
import com.jcaido.demorender.repositories.PiezaRepository;
import com.jcaido.demorender.repositories.ProveedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EnradaPiezaServiceImpl implements EntradaPiezaService {

    private final EntradaPiezaRepository entradaPiezaRepository;
    private final ProveedorRepository proveedorRepository;
    private final PiezaRepository piezaRepository;
    private final AlbaranProveedorRepository albaranProveedorRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService;

    public EnradaPiezaServiceImpl(EntradaPiezaRepository entradaPiezaRepository, ProveedorRepository proveedorRepository, PiezaRepository piezaRepository, AlbaranProveedorRepository albaranProveedorRepository, ModelMapper modelMapper, EntityManager entityManager, EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService) {
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.proveedorRepository = proveedorRepository;
        this.piezaRepository = piezaRepository;
        this.albaranProveedorRepository = albaranProveedorRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.entradaPiezaModificacionCambiosService = entradaPiezaModificacionCambiosService;
    }


    @Override
    public EntradaPiezaDTO crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idPieza, Long idAlbaranProvedor) {

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = modelMapper.map(entradaPiezaCrearDTO, EntradaPieza.class);
        Pieza pieza = piezaRepository.findById(idPieza).get();
        entradaPieza.setPieza(pieza);
        AlbaranProveedor albaranProveedor = albaranProveedorRepository.findById(idAlbaranProvedor).get();
        entradaPieza.setAlbaranProveedor(albaranProveedor);
        entradaPiezaRepository.save(entradaPieza);
        EntradaPiezaDTO entradaPiezaRespuesta = modelMapper.map(entradaPieza, EntradaPiezaDTO.class);

        return entradaPiezaRespuesta;
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> findAll() {
        List<EntradaPieza> entradaPieza = entradaPiezaRepository.findAll();

        return entradaPieza.stream().map(entrada-> modelMapper.map(entrada, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<EntradaPiezaBusquedasDTO> findById(Long id) {
        Optional<EntradaPieza> entradaPieza = entradaPiezaRepository.findById(id);

        if (!entradaPieza.isPresent())
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> obtenerEntradasPorPiezaHQL(Long id_pieza) {
        Query query = entityManager.createQuery("FROM EntradaPieza e WHERE e.pieza.id = :id" );
        query.setParameter("id", id_pieza);
        List<EntradaPieza> entradasPiezas = query.getResultList();

        return entradasPiezas.stream().map(entradaPieza-> modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> obtenerEntradasPiezasPorAlbaranProveedorHQL(Long idAlbaranProveedor) {
        Query query = entityManager.createQuery("FROM EntradaPieza e WHERE e.albaranProveedor.id = :idAlbaranProveedor" );
        query.setParameter("idAlbaranProveedor", idAlbaranProveedor);
        List<EntradaPieza> entradasPiezas = query.getResultList();

        return entradasPiezas.stream().map(entradaPieza-> modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public EntradaPiezaDTO modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idPieza) {
        if (entradaPiezaDTO.getId() == null)
            throw new BadRequestModificacionException("Entrada", "id");

        if (!entradaPiezaRepository.existsById(entradaPiezaDTO.getId()))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(entradaPiezaDTO.getId()));

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = entradaPiezaRepository.findById(entradaPiezaDTO.getId()).get();
        Pieza pieza = piezaRepository.findById(idPieza).get();
        entradaPieza.setPieza(pieza);
        entradaPieza.setCantidad(entradaPiezaDTO.getCantidad());
        entradaPieza.setPrecioEntrada(entradaPiezaDTO.getPrecioEntrada());

        EntradaPieza entradaPiezaModificada = entradaPiezaRepository.save(entradaPieza);
        EntradaPiezaDTO entradaPiezaModificadaDTO = modelMapper.map(entradaPiezaModificada, EntradaPiezaDTO.class);

        return entradaPiezaModificadaDTO;
    }

    @Override
    public String deleteById(Long id) {
        if (!entradaPiezaRepository.existsById(id))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(id));

        if (entradaPiezaModificacionCambiosService.validaciionEntradaPiezaAlbaranFacturado(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen albaranes facturados asociados con ese entrada de piezas");

        entradaPiezaRepository.deleteById(id);

        String respuesta = "entrada pieza eliminada con exito";

        return respuesta;
    }
}

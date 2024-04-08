package com.jcaido.demorender.services.facturaProveedor;

import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.FacturaProveedor;
import com.jcaido.demorender.models.Proveedor;
import com.jcaido.demorender.repositories.FacturaProveedorRepository;
import com.jcaido.demorender.repositories.ProveedorRepository;
import com.jcaido.demorender.services.albaranProveedor.AlbaranProveedorModificacionCambiosService;
import com.jcaido.demorender.services.albaranProveedor.AlbaranProveedorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaProveedorServiceImpl implements FacturaProveedorService {

    private final AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService;
    private final FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService;
    private final ModelMapper modelMapper;
    private final ProveedorRepository proveedorRepository;
    private final FacturaProveedorRepository facturaProveedorRepository;
    private final AlbaranProveedorService albaranProveedorService;
    private final EntityManager entityManager;

    public FacturaProveedorServiceImpl(AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService, FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService, ModelMapper modelMapper, ProveedorRepository proveedorRepository, FacturaProveedorRepository facturaProveedorRepository, AlbaranProveedorService albaranProveedorService, EntityManager entityManager) {
        this.albaranProveedorModificacionCambiosService = albaranProveedorModificacionCambiosService;
        this.facturaProveedorModificacionCambiosService = facturaProveedorModificacionCambiosService;
        this.modelMapper = modelMapper;
        this.proveedorRepository = proveedorRepository;
        this.facturaProveedorRepository = facturaProveedorRepository;
        this.albaranProveedorService = albaranProveedorService;
        this.entityManager = entityManager;
    }


    @Override
    public FacturaProveedorDTO crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor) {

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResourceNotFoundException("Proveedor", "id", String.valueOf(idProveedor));

        if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorCrearDTO.getNumeroFactura(), idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe para ese proveedor");

        FacturaProveedor facturaProveedor = modelMapper.map(facturaProveedorCrearDTO, FacturaProveedor.class);
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        facturaProveedor.setProveedor(proveedor);
        facturaProveedorRepository.save(facturaProveedor);

        FacturaProveedorDTO facturaProveedorRespuesta = modelMapper.map(facturaProveedor, FacturaProveedorDTO.class);

        return facturaProveedorRespuesta;
    }

    @Override
    public List<FacturaProveedorBusquedasDTO> findAll() {
        List<FacturaProveedor> facturas = facturaProveedorRepository.findAll();

        return facturas.stream().map(factura -> modelMapper.map(factura, FacturaProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public FacturaProveedorBusquedasDTO findById(Long id) {
        Optional<FacturaProveedor> facturasProveedor = facturaProveedorRepository.findById(id);

        if (!facturasProveedor.isPresent())
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(id));

        FacturaProveedorBusquedasDTO facturaProveedorEncontrada = modelMapper.map(facturasProveedor, FacturaProveedorBusquedasDTO.class);

        return facturaProveedorEncontrada;
    }

    @Override
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedoresEntreFechas(LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal) {
        Query query = entityManager.createQuery("FROM FacturaProveedor f " +
                "WHERE f.fechaFactura >= :fechaFacturaInicial AND f.fechaFactura <= :fechaFacturaFinal " +
                "ORDER BY f.fechaFactura asc");
        query.setParameter("fechaFacturaInicial", fechaFacturaInicial);
        query.setParameter("fechaFacturaFinal", fechaFacturaFinal);

        List<FacturaProveedor> facturasProveedor = query.getResultList();

        return facturasProveedor.stream().map(facturaProveedor-> modelMapper.map(facturaProveedor, FacturaProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasPorProveedorEntreFechas(Long idProveedor, LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal) {
        Query query = entityManager.createQuery("FROM FacturaProveedor f " +
                "WHERE f.proveedor.id = :idProveedor AND f.fechaFactura >= :fechaFacturaInicial " +
                "AND f.fechaFactura <= :fechaFacturaFinal " +
                "ORDER BY f.fechaFactura asc");
        query.setParameter("idProveedor", idProveedor);
        query.setParameter("fechaFacturaInicial", fechaFacturaInicial);
        query.setParameter("fechaFacturaFinal", fechaFacturaFinal);

        List<FacturaProveedor> facturasProveedor = query.getResultList();

        return facturasProveedor.stream().map(facturaProveedor -> modelMapper.map(facturaProveedor, FacturaProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public FacturaProveedorDTO modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor) {
        if (facturaProveedorDTO.getId() ==  null)
            throw new BadRequestModificacionException("Factura de proveedor", "id");

        if (!facturaProveedorRepository.existsById(facturaProveedorDTO.getId()))
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(facturaProveedorDTO.getId()));

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la factura no existe");

        if (facturaProveedorModificacionCambiosService.numeroFacturaHaCambiado(facturaProveedorDTO)) {
            if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorDTO.getNumeroFactura(), idProveedor))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe para ese proveedor");
        }

        if (!facturaProveedorModificacionCambiosService.proveedorHaCambiado(facturaProveedorDTO, idProveedor)) {
            if (!facturaProveedorModificacionCambiosService.validacionAlbaranes(facturaProveedorDTO.getId()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La factura tiene albaranes asociados");
        }

        FacturaProveedor facturaProveedor = facturaProveedorRepository.findById(facturaProveedorDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();

        facturaProveedor.setProveedor(proveedor);
        facturaProveedor.setFechaFactura(facturaProveedorDTO.getFechaFactura());
        facturaProveedor.setNumeroFactura(facturaProveedorDTO.getNumeroFactura());
        facturaProveedor.setTipoIVA(facturaProveedorDTO.getTipoIVA());
        facturaProveedor.setContabilizada(facturaProveedorDTO.getContabilizada());

        FacturaProveedor facturaProveedorModificada = facturaProveedorRepository.save(facturaProveedor);
        FacturaProveedorDTO facturaProveedorModificadaDTO = modelMapper.map(facturaProveedorModificada, FacturaProveedorDTO.class);

        return facturaProveedorModificadaDTO;
    }

    @Override
    public String deleteById(Long id) {
        if (!facturaProveedorRepository.existsById(id))
            throw new ResourceNotFoundException("Factura", "id", String.valueOf(id));

        if (albaranProveedorService.obtenerAlbaranesProveedorPorFacturaProveedorHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen albaranes asociados con esa factura de proveedor");

        //TODO: Validad que la factura no esté contabilizada

        facturaProveedorRepository.deleteById(id);

        String respuesta = "Factura de proveedor eliminada con exito";

        return respuesta;
    }
}

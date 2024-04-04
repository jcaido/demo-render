package com.jcaido.demorender.services.facturaCliente;

import com.jcaido.demorender.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.jcaido.demorender.DTOs.facturaCliente.FacturaClienteDTO;
import com.jcaido.demorender.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.jcaido.demorender.exceptions.BadRequestModificacionException;
import com.jcaido.demorender.exceptions.ResourceNotFoundException;
import com.jcaido.demorender.models.FacturaCliente;
import com.jcaido.demorender.models.OrdenReparacion;
import com.jcaido.demorender.models.Propietario;
import com.jcaido.demorender.repositories.FacturaClienteRepository;
import com.jcaido.demorender.repositories.OrdenReparacionRepository;
import com.jcaido.demorender.repositories.PropietarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaClienteServiceImpl implements FacturaClienteService {

    private final PropietarioRepository propietarioRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final FacturaClienteRepository facturaClienteRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final FacturaClienteValidacionesService facturaClienteValidacionesService;
    private final FacturaClienteConsultasService facturaClienteConsultasService;

    public FacturaClienteServiceImpl(PropietarioRepository propietarioRepository, OrdenReparacionRepository ordenReparacionRepository, FacturaClienteRepository facturaClienteRepository, EntityManager entityManager, ModelMapper modelMapper, FacturaClienteValidacionesService facturaClienteValidacionesService, FacturaClienteConsultasService facturaClienteConsultasService) {
        this.propietarioRepository = propietarioRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.facturaClienteRepository = facturaClienteRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.facturaClienteValidacionesService = facturaClienteValidacionesService;
        this.facturaClienteConsultasService = facturaClienteConsultasService;
    }


    @Override
    @Transactional
    public FacturaClienteDTO crearFacturaCliente(FacturaClienteCrearDTO facturaClienteCrearDTO, Long idPropietario, Long idOrdenReparacion) {

        if (!facturaClienteValidacionesService.validacionPropietario(idPropietario))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(idPropietario));

        if (!facturaClienteValidacionesService.validacionOrdenReparacion(idOrdenReparacion))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(idOrdenReparacion));

        if (!facturaClienteValidacionesService.validacionOrdenReparacionPerteneceAPropietario(idPropietario, idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación no pertenece al propietario");

        if (facturaClienteValidacionesService.validacionOrdenReparacionCerrada(idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación no está cerrada");

        if (!facturaClienteValidacionesService.validacionOrdenReparacionFacturada(idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación ya está facturada");

        if (!facturaClienteValidacionesService.validacionFechaFacturaClienteCrear(facturaClienteCrearDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha factura no puede ser inferior a la última factura de la serie");

        Propietario propietario = propietarioRepository.findById(idPropietario).get();
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteCrearDTO, FacturaCliente.class);
        facturaCliente.setPropietario(propietario);
        facturaCliente.setOrdenReparacion(ordenReparacion);
        facturaCliente.setSerie("T" + Integer.toString(facturaClienteCrearDTO.getFechaFactura().getYear()));
        facturaCliente.getOrdenReparacion().setFacturada(true);
        facturaClienteConsultasService.asignarNumeroFacturaCrearFactura(facturaCliente, facturaClienteCrearDTO);

        FacturaClienteDTO facturaClienteRespuesta = modelMapper.map(facturaCliente, FacturaClienteDTO.class);

        return facturaClienteRespuesta;
    }

    @Override
    public List<FacturaClientesBusquedasDTO> findAll() {
        List<FacturaCliente> facturasCliente = facturaClienteRepository.findAll();

        return facturasCliente.stream().map(factura -> modelMapper.map(factura, FacturaClientesBusquedasDTO.class)).toList();
    }

    @Override
    public List<FacturaClientesBusquedasDTO> obtenerFacturasClientesEntreFechas(LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal) {
        Query query = entityManager.createQuery("FROM FacturaCliente f " +
                "WHERE f.fechaFactura >= :fechaFacturaInicial AND f.fechaFactura <= :fechaFacturaFinal " +
                "ORDER BY f.fechaFactura asc");
        query.setParameter("fechaFacturaInicial", fechaFacturaInicial);
        query.setParameter("fechaFacturaFinal", fechaFacturaFinal);

        List<FacturaCliente> facturasClientes = query.getResultList();

        return facturasClientes.stream().map(facturaCliente-> modelMapper.map(facturaCliente, FacturaClientesBusquedasDTO.class)).toList();
    }

    @Override
    public List<FacturaClientesBusquedasDTO> obtenerFacturasPorClienteEntreFechas(Long idCliente, LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal) {
        Query query = entityManager.createQuery("FROM FacturaCliente f " +
                "WHERE f.ordenReparacion.vehiculo.propietario.id = :idCliente AND " +
                "f.fechaFactura >= :fechaFacturaInicial AND f.fechaFactura <= :fechaFacturaFinal " +
                "ORDER BY f.fechaFactura asc");
        query.setParameter("idCliente", idCliente);
        query.setParameter("fechaFacturaInicial", fechaFacturaInicial);
        query.setParameter("fechaFacturaFinal", fechaFacturaFinal);

        List<FacturaCliente> facturasClientes = query.getResultList();

        return facturasClientes.stream().map(facturaCliente-> modelMapper.map(facturaCliente, FacturaClientesBusquedasDTO.class)).toList();
    }

    @Override
    public FacturaClientesBusquedasDTO findById(Long id) {
        Optional<FacturaCliente> facturaCliente = facturaClienteRepository.findById(id);

        if (!facturaCliente.isPresent())
            throw new ResourceNotFoundException("Factura de cliente", "id", String.valueOf(id));

        FacturaClientesBusquedasDTO facturaClienteEncontrada = modelMapper.map(facturaCliente, FacturaClientesBusquedasDTO.class);

        return facturaClienteEncontrada;
    }

    @Override
    public FacturaClientesBusquedasDTO obtenerUltimaFacturaCliente() {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes ORDER BY id DESC LIMIT 1", FacturaCliente.class);
        FacturaCliente ultimaFactura = (FacturaCliente) query.getSingleResult();

        FacturaClientesBusquedasDTO facturaClienteEncontrada = modelMapper.map(ultimaFactura, FacturaClientesBusquedasDTO.class);

        return facturaClienteEncontrada;
    }

    @Override
    @Transactional
    public FacturaClienteDTO modificarFacturaCliente(FacturaClienteDTO facturaClienteDTO, Long idOrdenReparacion) {

        if (facturaClienteDTO.getId() ==  null)
            throw new BadRequestModificacionException("Factura de cliente", "id");

        if (!facturaClienteValidacionesService.validacionOrdenReparacion(idOrdenReparacion))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(idOrdenReparacion));

        if (facturaClienteValidacionesService.validacionOrdenReparacionCerrada(idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación no está cerrada");

        if (!facturaClienteValidacionesService.validacionOrdenReparacionFacturadaModificar(idOrdenReparacion, facturaClienteDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación ya está facturada");

        if (!facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO)
                && facturaClienteDTO.getFechaFactura().isAfter(facturaClienteConsultasService.obtenerFacturaPosterior(facturaClienteDTO).getFechaFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o inferior a la de la factura posterior");

        if (!facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteDTO.getFechaFactura().isBefore(facturaClienteConsultasService.obtenerFacturaAnterior(facturaClienteDTO).getFechaFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o superior a la de la factura anterior");

        if (facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO))
            if (facturaClienteDTO.getFechaFactura().isAfter(facturaClienteConsultasService.obtenerFacturaPosterior(facturaClienteDTO).getFechaFactura())
                    || facturaClienteDTO.getFechaFactura().isBefore(facturaClienteConsultasService.obtenerFacturaAnterior(facturaClienteDTO).getFechaFactura()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o inferior a la de la factura posterior e igual o superior a la de la fecha anterior");

        FacturaCliente facturaAModificar = facturaClienteRepository.findById(facturaClienteDTO.getId()).get();
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteDTO, FacturaCliente.class);

        OrdenReparacion ordenReparacionAnterior = facturaAModificar.getOrdenReparacion();
        ordenReparacionAnterior.setFacturada(false);
        ordenReparacionRepository.save(ordenReparacionAnterior);

        facturaCliente.setOrdenReparacion(ordenReparacion);
        facturaCliente.getOrdenReparacion().setFacturada(true);
        facturaCliente.setTipoIVA(facturaClienteDTO.getTipoIVA());
        facturaCliente.setSerie(facturaAModificar.getSerie());
        facturaCliente.setNumeroFactura(facturaAModificar.getNumeroFactura());
        facturaCliente.setFechaFactura(facturaClienteDTO.getFechaFactura());

        FacturaCliente facturaClienteModificada = facturaClienteRepository.save(facturaCliente);
        FacturaClienteDTO facturaClienteModificadaDTO = modelMapper.map(facturaClienteModificada, FacturaClienteDTO.class);

        return facturaClienteModificadaDTO;
    }

    @Override
    @Transactional
    public FacturaClienteDTO modificarFacturaClienteNoOR(FacturaClienteDTO facturaClienteDTO) {

        if (facturaClienteDTO.getId() ==  null)
            throw new BadRequestModificacionException("Factura de cliente", "id");

        if (!facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO)
                && facturaClienteDTO.getFechaFactura().isAfter(facturaClienteConsultasService.obtenerFacturaPosterior(facturaClienteDTO).getFechaFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o inferior a la de la factura posterior");

        if (!facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteDTO.getFechaFactura().isBefore(facturaClienteConsultasService.obtenerFacturaAnterior(facturaClienteDTO).getFechaFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o superior a la de la factura anterior");

        if (facturaClienteValidacionesService.facturaAnterior(facturaClienteDTO)
                && facturaClienteValidacionesService.facturaPosterior(facturaClienteDTO))
            if (facturaClienteDTO.getFechaFactura().isAfter(facturaClienteConsultasService.obtenerFacturaPosterior(facturaClienteDTO).getFechaFactura())
                    || facturaClienteDTO.getFechaFactura().isBefore(facturaClienteConsultasService.obtenerFacturaAnterior(facturaClienteDTO).getFechaFactura()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha debe ser igual o inferior a la de la factura posterior e igual o superior a la de la fecha anterior");

        FacturaCliente facturaAModificar = facturaClienteRepository.findById(facturaClienteDTO.getId()).get();
        OrdenReparacion ordenReparacion = facturaAModificar.getOrdenReparacion();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteDTO, FacturaCliente.class);

        facturaCliente.setOrdenReparacion(ordenReparacion);
        facturaCliente.setTipoIVA(facturaClienteDTO.getTipoIVA());
        facturaCliente.setSerie(facturaAModificar.getSerie());
        facturaCliente.setNumeroFactura(facturaAModificar.getNumeroFactura());
        facturaCliente.setFechaFactura(facturaClienteDTO.getFechaFactura());

        FacturaCliente facturaClienteModificada = facturaClienteRepository.save(facturaCliente);
        FacturaClienteDTO facturaClienteModificadaDTO = modelMapper.map(facturaCliente, FacturaClienteDTO.class);

        return facturaClienteModificadaDTO;
    }

    @Override
    @Transactional
    public String eliminarFacturaCliente(Long id) {

        if (!facturaClienteRepository.existsById(id))
            throw new ResourceNotFoundException("Factura", "id", String.valueOf(id));

        if (!facturaClienteValidacionesService.validacionUltimaFacturaAño(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solo se puede eliminar la ultima factura del año");

        OrdenReparacion ordenReparacionAsociada = facturaClienteRepository.findById(id).get().getOrdenReparacion();
        ordenReparacionAsociada.setFacturada(false);
        ordenReparacionRepository.save(ordenReparacionAsociada);

        facturaClienteRepository.deleteById(id);

        String respuesta = "Factura de cliente eliminada con exito";

        return respuesta;
    }
}

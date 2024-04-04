package com.jcaido.demorender.services.facturaCliente;

import com.jcaido.demorender.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.jcaido.demorender.DTOs.facturaCliente.FacturaClienteDTO;
import com.jcaido.demorender.models.FacturaCliente;
import com.jcaido.demorender.repositories.FacturaClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaClienteConsultasService {

    private final EntityManager entityManager;
    private final FacturaClienteRepository facturaClienteRepository;

    public FacturaClienteConsultasService(EntityManager entityManager, FacturaClienteRepository facturaClienteRepository) {
        this.entityManager = entityManager;
        this.facturaClienteRepository = facturaClienteRepository;
    }

    public List<FacturaCliente> obtenerFacturasClientesEntreFechas(FacturaClienteCrearDTO facturaDTO) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal");
        query.setParameter("fechaInicial", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> facturasYear = query.getResultList();

        return facturasYear;
    }

    public List<FacturaCliente> obtenerFacturaMaximoNumeroFacturaEntreFechas(FacturaClienteCrearDTO facturaDTO) {

        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal " +
                "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
        query.setParameter("fechaInicial", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> factura = query.getResultList();

        return factura;
    }

    public void asignarNumeroFacturaCrearFactura(FacturaCliente facturaCliente, FacturaClienteCrearDTO facturaClienteCrearDTO) {
        if (obtenerFacturasClientesEntreFechas(facturaClienteCrearDTO).isEmpty()) {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(1L);
            facturaClienteRepository.save(facturaCliente);
        } else {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).get(0).getNumeroFactura() + 1);
            facturaClienteRepository.save(facturaCliente);
        }
    }

    public FacturaCliente obtenerUltimaFacturaEntreFechas(FacturaClienteDTO facturaClienteDTO) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal " +
                "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
        query.setParameter("fechaInicial", LocalDate.of(facturaClienteDTO.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaClienteDTO.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> factura = query.getResultList();

        return factura.get(0);
    }

    public FacturaCliente obtenerUltimaFacturaAño(Long id) {
        FacturaCliente facturaCliente = facturaClienteRepository.findById(id).get();

        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal " +
                "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
        query.setParameter("fechaInicial", LocalDate.of(facturaCliente.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaCliente.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> factura = query.getResultList();

        return factura.get(0);
    }

    public FacturaCliente obtenerFacturaAnterior(FacturaClienteDTO facturaClienteDTO) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE serie_factura = :serie " +
                "AND numero_factura = :numero", FacturaCliente.class);
        query.setParameter("serie", facturaClienteRepository.findById(facturaClienteDTO.getId()).get().getSerie());
        query.setParameter("numero", facturaClienteRepository.findById(facturaClienteDTO.getId()).get().getNumeroFactura() - 1);
        List<FacturaCliente> factura = query.getResultList();

        if (factura.isEmpty())
            return null;

        return factura.get(0);
    }

    public FacturaCliente obtenerFacturaPosterior(FacturaClienteDTO facturaClienteDTO) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE serie_factura = :serie " +
                "AND numero_factura = :numero", FacturaCliente.class);
        query.setParameter("serie", facturaClienteRepository.findById(facturaClienteDTO.getId()).get().getSerie());
        query.setParameter("numero", facturaClienteRepository.findById(facturaClienteDTO.getId()).get().getNumeroFactura() + 1);
        List<FacturaCliente> factura = query.getResultList();

        if (factura.isEmpty())
            return null;

        return factura.get(0);
    }
}

package com.jcaido.demorender.services.facturaProveedor;

import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.jcaido.demorender.models.AlbaranProveedor;
import com.jcaido.demorender.models.FacturaProveedor;
import com.jcaido.demorender.repositories.FacturaProveedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaProveedorModificacionCambiosService {

    private final EntityManager entityManager;
    private final FacturaProveedorRepository facturaProveedorRepository;

    public FacturaProveedorModificacionCambiosService(EntityManager entityManager, FacturaProveedorRepository facturaProveedorRepository) {
        this.entityManager = entityManager;
        this.facturaProveedorRepository = facturaProveedorRepository;
    }

    public boolean validacionNumeroFactura(String numeroFactura, Long idProveedor) {

        Query query = entityManager.createQuery("FROM FacturaProveedor f WHERE f.numeroFactura = :numeroFactura" +
                " AND f.proveedor.id = :idProveedor");
        query.setParameter("numeroFactura", numeroFactura);
        query.setParameter("idProveedor", idProveedor);
        List<FacturaProveedor> facturasProveedor = query.getResultList();

        if (facturasProveedor.size() > 0)
            return false;

        return true;
    }

    public boolean validacionAlbaranes(Long idFactura) {

        FacturaProveedor factura = facturaProveedorRepository.findById(idFactura).get();
        List<AlbaranProveedor> albaranes = factura.getAlbaranesProveedores();

        if (albaranes.size() > 0)
            return false;

        return true;
    }

    public boolean proveedorHaCambiado(FacturaProveedorDTO facturaDTO, Long idProveedor) {
        FacturaProveedor factura = facturaProveedorRepository.findById(facturaDTO.getId()).get();

        if (factura.getProveedor().getId().equals(idProveedor))
            return true;

        return false;

    }

    public boolean numeroFacturaHaCambiado(FacturaProveedorDTO facturaDTO) {
        FacturaProveedor factura = facturaProveedorRepository.findById(facturaDTO.getId()).get();

        if (factura.getNumeroFactura().equals(facturaDTO.getNumeroFactura()))
            return false;

        return true;
    }
}

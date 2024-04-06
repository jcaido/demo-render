package com.jcaido.demorender.services.albaranProveedor;

import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.jcaido.demorender.models.AlbaranProveedor;
import com.jcaido.demorender.models.Proveedor;
import com.jcaido.demorender.repositories.AlbaranProveedorRepository;
import com.jcaido.demorender.repositories.ProveedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbaranProveedorModificacionCambiosService {

    private final ProveedorRepository proveedorRepository;
    private final AlbaranProveedorRepository albaranProveedorRepository;
    private final EntityManager entityManager;

    public AlbaranProveedorModificacionCambiosService(ProveedorRepository proveedorRepository, AlbaranProveedorRepository albaranProveedorRepository, EntityManager entityManager) {
        this.proveedorRepository = proveedorRepository;
        this.albaranProveedorRepository = albaranProveedorRepository;
        this.entityManager = entityManager;
    }

    public boolean validacionProveedor(Long idProveedor) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(idProveedor);

        if (proveedor.isPresent())
            return true;

        return false;
    }

    public boolean validacionNumeroAlbaran(String numeroAlbaran, Long idProveedor) {

        Query query = entityManager.createQuery("FROM AlbaranProveedor a WHERE a.numeroAlbaran = :numeroAlbaran" +
                " AND a.proveedor.id = :idProveedor");
        query.setParameter("numeroAlbaran", numeroAlbaran);
        query.setParameter("idProveedor", idProveedor);
        List<AlbaranProveedor> albaranesProveedor = query.getResultList();

        if (albaranesProveedor.size() > 0)
            return false;

        return true;
    }

    public boolean numeroAlbaranHaCambiado(AlbaranProveedorDTO albaranDTO) {
        AlbaranProveedor albaran = albaranProveedorRepository.findById(albaranDTO.getId()).get();

        if (albaran.getNumeroAlbaran().equals(albaranDTO.getNumeroAlbaran()))
            return false;

        return true;
    }
}

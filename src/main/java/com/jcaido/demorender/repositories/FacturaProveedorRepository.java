package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.FacturaProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, Long> {

    FacturaProveedor findByNumeroFactura(String numeroFactura);
    boolean existsById(Long id);
}

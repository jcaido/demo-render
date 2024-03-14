package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Transactional(readOnly = true)
    Optional<Proveedor> findByDniCif(String dniCif);

    @Transactional(readOnly = true)
    List<Proveedor> findByNombre(String nombre);

    @Transactional(readOnly = true)
    boolean existsById(Long id);

    @Transactional(readOnly = true)
    boolean existsByDniCif(String dniCif);
}

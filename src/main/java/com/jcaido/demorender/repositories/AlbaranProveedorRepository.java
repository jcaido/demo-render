package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.AlbaranProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AlbaranProveedorRepository extends JpaRepository<AlbaranProveedor, Long> {

    @Transactional(readOnly = true)
    List<AlbaranProveedor> findByNumeroAlbaran(String numeroAlbaran);

    @Transactional(readOnly = true)
    boolean existsById(Long id);
}

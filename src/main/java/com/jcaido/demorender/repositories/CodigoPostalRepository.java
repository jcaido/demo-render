package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodigoPostalRepository extends JpaRepository<CodigoPostal, Long> {

    @Transactional(readOnly = true)
    Optional<CodigoPostal> findByCodigo(String codigo);

    @Transactional(readOnly = true)
    List<CodigoPostal> findByProvincia (String provincia);

    @Transactional(readOnly = true)
    Optional<CodigoPostal> findByLocalidad(String Localidad);

    @Transactional(readOnly = true)
    boolean existsByCodigo(String codigo);

    @Transactional(readOnly = true)
    boolean existsByLocalidad(String localidad);
}

package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PiezaRepository extends JpaRepository<Pieza, Long> {

    @Transactional(readOnly = true)
    Optional<Pieza> findByReferencia(String referencia);
    @Transactional(readOnly = true)
    List<Pieza> findByNombre (String nombre);
    @Transactional(readOnly = true)
    boolean existsByReferencia(String referencia);
    @Transactional(readOnly = true)
    boolean existsByNombre(String nombre);
    @Transactional(readOnly = true)
    boolean existsById(Long id);
}

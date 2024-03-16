package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Transactional(readOnly = true)
    Optional<Vehiculo> findByMatricula(String matricula);
    @Transactional(readOnly = true)
    List<Vehiculo> findByMarca(String marca);
    @Transactional(readOnly = true)
    List<Vehiculo> findByMarcaAndModelo(String marca, String modelo);
    @Transactional(readOnly = true)
    boolean existsById(Long id);
    @Transactional(readOnly = true)
    boolean existsByMatricula(String matricula);
}

package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.OrdenReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, Long> {

    @Transactional(readOnly = true)
    List<OrdenReparacion> findByFechaApertura (LocalDate fechaApertura);
    @Transactional(readOnly = true)
    List<OrdenReparacion> findByFechaCierre (LocalDate fechaCierre);
    @Transactional(readOnly = true)
    List<OrdenReparacion> findByCerrada (boolean cerrada);
    @Transactional(readOnly = true)
    List<OrdenReparacion> findByCerradaOrderByFechaAperturaAsc (boolean cerrada);
    @Transactional(readOnly = true)
    boolean existsById(Long id);
}

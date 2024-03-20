package com.jcaido.demorender.repositories;

import com.jcaido.demorender.models.ManoDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManoDeObraRepository extends JpaRepository<ManoDeObra, Long> {

    @Transactional(readOnly = true)
    boolean existsByprecioHoraClienteTaller(Double precio);

    @Transactional(readOnly = true)
    Optional<ManoDeObra> findByPrecioHoraClienteTallerActual(boolean precioHoraClienteTallerActual);

    @Transactional
    @Modifying
    @Query(value = "UPDATE mano_de_obra SET precio_hora_actual = :precioHoraActual", nativeQuery = true)
    void PrecioHoraActualFalse(@Param("precioHoraActual") boolean precioHoraActual);

    @Transactional(readOnly = true)
    List<ManoDeObra> findAllByOrderByFechaNuevoPrecioDesc();
}

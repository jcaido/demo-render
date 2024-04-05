package com.jcaido.demorender.repositories;

import com.jcaido.demorender.DTOs.almacen.MovimientoAlmacenDTO;
import com.jcaido.demorender.DTOs.almacen.MovimientoPiezaDTO;
import com.jcaido.demorender.models.EntradaPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntradaPiezaRepository extends JpaRepository<EntradaPieza, Long> {

    @Query("SELECT new com.jcaido.demorender.DTOs.almacen.MovimientoAlmacenDTO(" +
            " e.pieza.referencia, e.pieza.nombre, SUM(e.cantidad))" +
            " FROM EntradaPieza AS e" +
            " WHERE e.albaranProveedor.fechaAlbaran <= :fecha" +
            " GROUP BY e.pieza.referencia, e.pieza.nombre")
    List<MovimientoAlmacenDTO> obtenerTotalEntradasFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT new com.jcaido.demorender.DTOs.almacen.MovimientoPiezaDTO(" +
            " e.albaranProveedor.fechaAlbaran, e.albaranProveedor.proveedor.nombre, e.cantidad)" +
            " FROM EntradaPieza AS e" +
            " WHERE e.pieza.referencia =:referencia")
    List<MovimientoPiezaDTO> obtenerEntradasPorPieza(@Param("referencia") String referencia);
}

package com.jcaido.demorender.services.almacen;

import com.jcaido.demorender.DTOs.almacen.MovimientoAlmacenDTO;
import com.jcaido.demorender.DTOs.almacen.MovimientoPiezaDTO;

import java.time.LocalDate;
import java.util.List;

public interface InventarioAlmacenService {
    List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(LocalDate fecha);
    List<MovimientoPiezaDTO> obtenerMovimientosPorPieza(String referencia);
}

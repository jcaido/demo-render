package com.jcaido.demorender.services.piezasReparacion;

import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionCrearDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionDTO;

import java.time.LocalDate;
import java.util.List;

public interface PiezasReparacionService {

    PiezasReparacionDTO crearPiezasReparacion(PiezasReparacionCrearDTO piezasReparacionCrearDTO, Long id_ordenReparacion, Long id_pieza);
    List<PiezasReparacionBusquedasParcialDTO> findAll();
    PiezasReparacionBusquedasParcialDTO findById(Long id);
    List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(Long id);
    List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorPiezaHQL(Long id_pieza);
    List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorOrdenReparacion(LocalDate fecha);
    List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorPiezaYOrdenReparacion(String referencia);
    String deleteById(Long id);
}

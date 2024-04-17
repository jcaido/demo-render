package com.jcaido.demorender.services.ordenReparacion;

import com.jcaido.demorender.DTOs.ordenReparacion.*;

import java.time.LocalDate;
import java.util.List;

public interface OrdenReparacionService {

    OrdenReparacionDTO crearOrdenReparacion(OrdenReparacionCrearDTO ordenReparacionCrearDTO, Long idVehiculo);
    List<OrdenReparacionBusquedasDTO> findAll();
    OrdenReparacionBusquedasDTO findById(Long id);
    OrdenReparacionBusquedasParcialDTO findByIdParcial(Long id);
    List<OrdenReparacionBusquedasDTO> findByFechaApertura(LocalDate fechaApertura);
    List<OrdenReparacionBusquedasDTO> findByFechaCierre(LocalDate fechaCierre);
    List<OrdenReparacionBusquedasDTO> findByCerrada(Boolean cerrada);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcial(Boolean cerrada);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialByFechaAperturaAsc(Boolean cerrada);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorFechaApertura(Boolean cerrada, LocalDate fechaApertura);
    List<OrdenReparacionBusquedasDTO> findByCerradaEntreFechasDeCierre(Boolean cerrada, LocalDate fechaCierreInicical, LocalDate fechaCierreFinal);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorVehiculo(Boolean cerrada, Long id_vehiculo);
    List<OrdenReparacionBusquedasDTO> findByCerradaPorVehiculo(Boolean cerrada, Long id_vehiculo);
    List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(Long id_vehiculo);
    List<OrdenReparacionReducidaDTO> obtenerOrdenesReparacionCerradasPtesFacturar();
    OrdenReparacionDTO modificarOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long id_vehiculo);
    OrdenReparacionDTO modificarOrdenReparacionHoras(OrdenReparacionHorasDTO ordenReparacionHorasDTO);
    OrdenReparacionDTO modificarOrdenReparacionCierre(OrdenReparacionCierreDTO ordenReparacionCierreDTO);
    OrdenReparacionDTO modificarOrdenReparacionAbrir(OrdenReparacionCierreDTO ordenReparacionCierreDTO);
    String deleteById(Long  id);
}

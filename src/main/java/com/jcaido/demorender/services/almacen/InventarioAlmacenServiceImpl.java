package com.jcaido.demorender.services.almacen;

import com.jcaido.demorender.DTOs.almacen.MovimientoAlmacenDTO;
import com.jcaido.demorender.DTOs.almacen.MovimientoPiezaDTO;
import com.jcaido.demorender.repositories.EntradaPiezaRepository;
import com.jcaido.demorender.repositories.PiezasReparacionRepository;
import com.jcaido.demorender.services.piezasReparacion.PiezasReparacionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

@Service
public class InventarioAlmacenServiceImpl implements InventarioAlmacenService{

    private final EntradaPiezaRepository entradaPiezaRepository;
    private final PiezasReparacionRepository piezasReparacionRepository;
    private final PiezasReparacionService piezasReparacionService;

    public InventarioAlmacenServiceImpl(EntradaPiezaRepository entradaPiezaRepository, PiezasReparacionRepository piezasReparacionRepository, PiezasReparacionService piezasReparacionService) {
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.piezasReparacionRepository = piezasReparacionRepository;
        this.piezasReparacionService = piezasReparacionService;
    }

    @Override
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(LocalDate fecha) {
        if (piezasReparacionService.obtenerPiezasReparacionPorOrdenReparacion(fecha).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen piezas imputadas a Ordenes de Reparación abiertas " +
                    "con fecha igual o anterior a la fecha solicitada. Debe cerrar esas órdenes para poder obtener " +
                    "el inventario a la fecha solicitada. La fecha de cierre debe ser la del inventario.");

        List<MovimientoAlmacenDTO> entradas = entradaPiezaRepository.obtenerTotalEntradasFecha(fecha);

        List<MovimientoAlmacenDTO> salidas = piezasReparacionRepository.obtenerTotalPiezasReparacionFecha(fecha);

        for (MovimientoAlmacenDTO salida: salidas
        ) {
            salida.setTotal(-salida.getTotal());
        }

        List<MovimientoAlmacenDTO> inventario = Stream.of(entradas, salidas).flatMap(Collection::stream).toList();

        Map<MovimientoAlmacenDTO.Movimiento, Long> inventarioMapGroupSum = inventario.stream().collect(groupingBy(mov-> new MovimientoAlmacenDTO.Movimiento(mov.getPiezaReferencia(), mov.getPiezaNombre()), summingLong(MovimientoAlmacenDTO::getTotal)));

        List<MovimientoAlmacenDTO> inventarioAlmacen = new ArrayList<>();
        inventarioMapGroupSum.forEach((k, v)-> {
            inventarioAlmacen.add(new MovimientoAlmacenDTO(k.piezaReferencia(), k.piezaNombre(), v));
        });

        return inventarioAlmacen.stream().filter(movimiento-> movimiento.getTotal() != 0).toList();
    }

    @Override
    public List<MovimientoPiezaDTO> obtenerMovimientosPorPieza(String referencia) {
        if (piezasReparacionService.obtenerPiezasReparacionPorPiezaYOrdenReparacion(referencia).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen Ordenes de Reparación abiertas " +
                    "con la pieza solicitada imputada. Debe cerrar esas órdenes para poder obtener " +
                    "los movimientos de esa pieza.");

        List<MovimientoPiezaDTO> entradas = entradaPiezaRepository.obtenerEntradasPorPieza(referencia);

        List<MovimientoPiezaDTO> salidas = piezasReparacionRepository.obtenerPiezasReparacionPorPieza(referencia);

        for (MovimientoPiezaDTO salida: salidas
        ) {
            salida.setCantidad(-salida.getCantidad());
        }

        List<MovimientoPiezaDTO> movimientos = Stream.of(entradas, salidas).flatMap(Collection::stream).toList();

        List<MovimientoPiezaDTO> movimientosSort = movimientos.stream().sorted(Comparator.comparing(MovimientoPiezaDTO::getFechaMovimiento)).collect(Collectors.toList());

        return movimientosSort;
    }
}

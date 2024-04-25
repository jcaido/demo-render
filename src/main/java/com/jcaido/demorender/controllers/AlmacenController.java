package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.almacen.MovimientoAlmacenDTO;
import com.jcaido.demorender.DTOs.almacen.MovimientoPiezaDTO;
import com.jcaido.demorender.services.almacen.InventarioAlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/almacen")
public class AlmacenController {

    private final InventarioAlmacenService inventarioAlmacenService;

    public AlmacenController(InventarioAlmacenService inventarioAlmacenService) {
        this.inventarioAlmacenService = inventarioAlmacenService;
    }

    @Operation(summary = "Obtener el inventario de almacén actual", description = "Obtener el inventario de almacén actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario de almacén obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovimientoAlmacenDTO.class))
                    }),
    })
    @GetMapping("/inventario")
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacen() {

        return inventarioAlmacenService.obtenerInventarioAlmacenFecha(LocalDate.now());
    }


    @Operation(summary = "Obtener el inventario de almacén a una fecha determinada", description = "Obtener el inventario de almacén a una fecha determinada" +
            " formato fecha aaaa-mm-dd")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario de almacén obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovimientoAlmacenDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT Existen piezas imputadas a Ordenes de Reparación abiertas " +
                            "con fecha igual o anterior a la fecha solicitada. Debe cerrar esas órdenes para poder obtener " +
                            "el inventario a la fecha solicitada. La fecha de cierre debe ser la del inventario.",
                    content = @Content),
    })
    @GetMapping("/inventario/{fecha}")
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(
            @Parameter(description = "fecha del inventario", required = true)
            @PathVariable(name="fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return inventarioAlmacenService.obtenerInventarioAlmacenFecha(fecha);
    }


    @Operation(summary = "Obtener los movimientos de almacén de una pieza determinada", description = "Obtener los movimientos de  almacén de una pieza determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movimientos de almacén obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovimientoPiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT Existen Ordenes de Reparación abiertas " +
                            "con la pieza solicitada imputada. Debe cerrar esas órdenes para poder obtener " +
                            "los movimientos de esa pieza.",
                    content = @Content),
    })
    @GetMapping("/movimientos/{pieza}")
    public List<MovimientoPiezaDTO> obtenerEntradasPorPieza(
            @Parameter(description = "referencia de la pieza ", required = true)
            @PathVariable(name="pieza") String pieza) {

        return inventarioAlmacenService.obtenerMovimientosPorPieza(pieza);
    }
}

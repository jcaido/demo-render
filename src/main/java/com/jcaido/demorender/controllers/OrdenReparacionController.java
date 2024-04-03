package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.ordenReparacion.*;
import com.jcaido.demorender.services.ordenReparacion.OrdenReparacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/ordenesReparacion")
public class OrdenReparacionController {

    private final OrdenReparacionService ordenReparacionService;

    public OrdenReparacionController(OrdenReparacionService ordenReparacionService) {
        this.ordenReparacionService = ordenReparacionService;
    }

    @Operation(summary = "Crear una nueva orden de reparación", description = "Crear una nueva orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "oreden de reparación creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT [El vehiculo asociado a la orden de reparacion no existe]",
                    content = @Content),
    })
    @PostMapping("/{idVehiculo}")
    public ResponseEntity<OrdenReparacionDTO> crearOrdenesReparacion(@Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO,
                                                                     @Parameter(description = "id del vehículo", required = true) @PathVariable Long idVehiculo) {

        return new ResponseEntity<>(ordenReparacionService.crearOrdenReparacion(ordenReparacionDTO, idVehiculo), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener una orden de reparación por su id", description = "Obtener una orden de reparación por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Orden de reparacion no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenReparacionBusquedasDTO> obtenerOrdenReparacionPorId(
            @Parameter(description = "id de la orden de reparación", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una orden de reparación parcial por su id", description = " solo se incluyen los campos campos id, fecha apertura, descripción, kilómetros, matrícula, marca, modelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasParcialDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Orden de reparacion no encontrada",
                    content = @Content)
    })
    @GetMapping("/parcial/{id}")
    public ResponseEntity<OrdenReparacionBusquedasParcialDTO> obtenerOrdenReparacionPorIdParcial(
            @Parameter(description = "id de la orden de reparación", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.findByIdParcial(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación", description = "Obtener una lista con todas las órdenes de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping
    public List<OrdenReparacionBusquedasDTO> obtenerTodasLasOrdenesDeReparacion() {

        return ordenReparacionService.findAll();
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por fecha de apertura",
            description = "Obtener una lista con todas las órdenes de reparación por fecha de apertura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/fechaapertura")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaAperturaRP(
            @Parameter(description = "fecha de apertura, formato aaaa-mm-dd", required = true)
            @RequestParam(value="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByFechaApertura(fechaApertura);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por fecha de apertura",
            description = "Obtener una lista con todas las órdenes de reparación por fecha de apertura")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/fechaapertura/{fechaApertura}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaAperturaPV(
            @Parameter(description = "fecha de apertura, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByFechaApertura(fechaApertura);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por fecha de cierre",
            description = "Obtener una lista con todas las órdenes de reparación por fecha de cierre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/fechacierre/{fechaCierre}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaCierre(
            @Parameter(description = "fecha de cierre, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaCierre")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierre) {

        return ordenReparacionService.findByFechaApertura(fechaCierre);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas)",
            description = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada/{cerrada}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerrada(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerrada(cerrada);
    }

    @Operation(summary = "Obtener una lista parcial con todas las órdenes de reparación por estado (abiertas o cerradas)",
            description = "Sólo se mostrarán los campos id, fecha apertura, descripción, kilómetros, matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada-parcial/{cerrada}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcial(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerradaParcial(cerrada);
    }

    @Operation(summary = "Obtener una lista parcial ordenada por fecha apertura con todas las órdenes de reparación por estado (abiertas o cerradas)",
            description = "Sólo se mostrarán los campos id, fecha apertura, descripción, kilómetros, matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada-parcial-sort/{cerrada}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialByFechaAperturaAsc(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerradaParcialByFechaAperturaAsc(cerrada);
    }

    @Operation(summary = "Obtener una lista parcial con todas las órdenes de reparación por estado (abiertas o cerradas) y por fecha de apertura",
            description = "Sólo se mostrarán los campos id, fecha apertura, descripción, kilómetros, matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada-parcial/{cerrada}/{fechaApertura}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialFechaApertura(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada,
            @Parameter(description = "fecha de apertura, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByCerradaParcialPorFechaApertura(cerrada, fechaApertura);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas) entre fechas de cierre",
            description = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas) entre fechas de cierre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada/{cerrada}/{fechaCierreInicial}/{fechaCierreFinal}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerradaEntreFechasDeCierre(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada,
            @Parameter(description = "fecha de cierre inicial, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaCierreInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierreInicial,
            @Parameter(description = "fecha de cierre final, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaCierreFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierreFinal) {

        return ordenReparacionService.findByCerradaEntreFechasDeCierre(cerrada, fechaCierreInicial, fechaCierreFinal);
    }

    @Operation(summary = "Obtener una lista parcial con todas las órdenes de reparación por estado (abiertas o cerradas) y por vehiculo",
            description = "Sólo se mostrarán los campos id, fecha apertura, descripción, kilómetros, matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada-parcial-vehiculo/{cerrada}/{id_vehiculo}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialVehiculo(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada,
            @Parameter(description = "id del vehículo", required = true)
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.findByCerradaParcialPorVehiculo(cerrada, id_vehiculo);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas) y por vehiculo",
            description = "Obtener una lista con todas las órdenes de reparación por estado (abiertas o cerradas) y por vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerrada-vehiculo/{cerrada}/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerradaVehiculo(
            @Parameter(description = "true = cerradas, false = abiertas", required = true)
            @PathVariable Boolean cerrada,
            @Parameter(description = "id del vehículo", required = true)
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.findByCerradaPorVehiculo(cerrada, id_vehiculo);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación por por vehiculo",
            description = "Obtener una lista con todas las órdenes de reparación por por vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/vehiculo/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(
            @Parameter(description = "id del vehículo", required = true)
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id_vehiculo);
    }

    @Operation(summary = "Obtener una lista con todas las órdenes de reparación cerradas pendientes de facturar",
            description = "Obtener una lista con todas las órdenes de reparación cerradas pendientes de facturar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Órdenes de reparación obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    })
    })
    @GetMapping("/cerradas-ptes-facturar")
    public List<OrdenReparacionReducidaDTO> obtenerOrdenesReparacionCerradasPtesFacturar() {

        return ordenReparacionService.obtenerOrdenesReparacionCerradasPtesFacturar();
    }

    @Operation(summary = "Modificar una orden de reparación", description = "Modificar una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orden de reparación no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT El vehiculo asociado a la orden de reparacion no existe",
                    content = @Content)
    })
    @PutMapping("/{id_vehiculo}")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacion(
            @Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO,
            @Parameter(description = "id del vehículo", required = true)
            @PathVariable Long id_vehiculo) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacion(ordenReparacionDTO, id_vehiculo), HttpStatus.OK);
    }

    @Operation(summary = "Modificar una orden de reparación, sólo las horas", description = "Modificar una orden de reparación, sólo las horas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orden de reparación no encontrada",
                    content = @Content),
    })
    @PutMapping("/horas")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionHoras(@RequestBody OrdenReparacionHorasDTO ordenReparacionHorasDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionHoras(ordenReparacionHorasDTO), HttpStatus.OK);
    }

    @Operation(summary = "Modificar una orden de reparación, sólo la fecha de cierre", description = "Modificar una orden de reparación, sólo la fecha de cierre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orden de reparación no encontrada",
                    content = @Content),
    })
    @PutMapping("/cierre")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionCierre(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionCierre(ordenReparacionCierreDTO), HttpStatus.OK);
    }

    @Operation(summary = "Modificar una orden de reparación, abrir orden", description = "Modificar una orden de reparación, abrir orden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orden de reparación no encontrada",
                    content = @Content),
    })
    @PutMapping("/abrir")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionAbrir(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionAbrir(ordenReparacionCierreDTO), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una orden de reparación", description = "Eliminar una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación eliminada correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Albarán de proveedor no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLIT [la orden de reparacion esta cerrada]," +
                    " [Existen piezas relacionadas con esa orden de reparacion]",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPropietario(
            @Parameter(description = "id de la orden de reparación", required = true)
            @PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.deleteById(id), HttpStatus.OK);
    }
}

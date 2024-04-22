package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionCrearDTO;
import com.jcaido.demorender.DTOs.piezasReparacion.PiezasReparacionDTO;
import com.jcaido.demorender.services.piezasReparacion.PiezasReparacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/piezas-reparacion")
public class PiezasReparacionController {

    private final PiezasReparacionService piezasReparacionService;

    public PiezasReparacionController(PiezasReparacionService piezasReparacionService) {
        this.piezasReparacionService = piezasReparacionService;
    }

    @Operation(summary = "imputar una pieza a una orden de reparación", description = "imputar una pieza a una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pieza imputada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "orden de reparacion y/o pieza no encontrados",
                    content = @Content)
    })
    @PostMapping("/{id_ordenReparacion}/{id_pieza}")
    public ResponseEntity<PiezasReparacionDTO> crearPiezasReparacion(
            @Valid @RequestBody PiezasReparacionCrearDTO piezasReparacionCrearDTO,
            @Parameter(description = "id de la orden de reparación", required = true)
            @PathVariable Long id_ordenReparacion,
            @Parameter(description = "id de la pieza a imputar", required = true)
            @PathVariable Long id_pieza) {

        return new ResponseEntity<>(piezasReparacionService.crearPiezasReparacion(piezasReparacionCrearDTO, id_ordenReparacion, id_pieza), HttpStatus.CREATED);
    }


    @Operation(summary = "imputar una pieza a una orden de reparación", description = "imputar una pieza a una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pieza imputada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "orden de reparacion y/o pieza no encontrados",
                    content = @Content)
    })
    @PostMapping()
    public ResponseEntity<PiezasReparacionDTO> crearPiezasReparacionRP(
            @Valid @RequestBody PiezasReparacionCrearDTO piezasReparacionCrearDTO,
            @Parameter(description = "id de la orden de reparación", required = true)
            @RequestParam(value="ordenReparacion") Long id_ordenReparacion,
            @Parameter(description = "id de la pieza a imputar", required = true)
            @RequestParam(value="pieza") Long id_pieza) {

        return new ResponseEntity<>(piezasReparacionService.crearPiezasReparacion(piezasReparacionCrearDTO, id_ordenReparacion, id_pieza), HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener todas las piezas imputadas", description = "Obtener todas las piezas imputadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Piezas imputadas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionBusquedasParcialDTO.class))
                    })
    })
    @GetMapping()
    public List<PiezasReparacionBusquedasParcialDTO> obtenerTodasLasPiezasReparacion() {

        return piezasReparacionService.findAll();
    }


    @Operation(summary = "Obtener imputacion de pieza por su id", description = "Obtener imputacion de pieza por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imputacion de pieza obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionBusquedasParcialDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "imputacion de pieza no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PiezasReparacionBusquedasParcialDTO> obtenerPiezaReparacionPorId(@Parameter(description = "id de la imputación de pieza a buscar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(piezasReparacionService.findById(id), HttpStatus.OK);
    }


    @Operation(summary = "Obtener piezas imputadas a una orden de reparación", description = "Obtener piezas imputadas a una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Piezas imputadas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionBusquedasParcialDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "id de la orden de reparacion no se encuentra",
                    content = @Content)
    })
    @GetMapping("/orden-reparacion/{id}")
    public List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(@Parameter(description = "id de la orden de reparación",
            required = true) @PathVariable Long id) {

        return piezasReparacionService.obtenerPiezasReparacionPorOrdenReparacion(id);
    }


    @Operation(summary = "Obtener Piezas imputadas por pieza", description = "Obtener Piezas imputadas por pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imputaciones de piezas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezasReparacionBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/pieza/{id_pieza}")
    public List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorPieza(@Parameter(description = "id de la pieza",
            required = true) @PathVariable Long id_pieza) {

        return piezasReparacionService.obtenerPiezasReparacionPorPiezaHQL(id_pieza);
    }


    @Operation(summary = "Eliminar una imputación de pieza a una orden de reparación",
            description = "Eliminar una imputación de pieza a una orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imputaciónn de pieza eliminada correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Imputación de pieza no encontrada",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPiezaReparacion(@Parameter(description = "id de la imputación de pieza a eliminar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(piezasReparacionService.deleteById(id), HttpStatus.OK);
    }
}

package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.pieza.PiezaCrearDTO;
import com.jcaido.demorender.DTOs.pieza.PiezaDTO;
import com.jcaido.demorender.services.pieza.PiezaService;
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
@RequestMapping("/api/piezas")
public class PiezaController {

    private final PiezaService piezaService;

    public PiezaController(PiezaService piezaService) {
        this.piezaService = piezaService;
    }

    @Operation(summary = "Crear una nueva Pieza", description = "Crear una nueva Pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "pieza creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping()
    public ResponseEntity<PiezaDTO> crearPieza(@Valid @RequestBody PiezaCrearDTO piezaCrearDTO) {

        return new ResponseEntity<>(piezaService.crearPieza(piezaCrearDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todas las Piezas", description = "Obtener todas las Piezas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Piezas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    })
    })
    @GetMapping()
    public List<PiezaDTO> obtenerTodasLasPiezas() {

        return piezaService.findAll();
    }

    @Operation(summary = "Obtener Pieza por id", description = "Obtener Pieza por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pieza obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "pieza no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PiezaDTO> obtenerPiezaPorId(@Parameter(description = "id de la pieza a buscar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(piezaService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener Pieza por referencia", description = "Obtener Pieza por referencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pieza obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "pieza no encontrada",
                    content = @Content)
    })
    @GetMapping("/referencia/{referencia}")
    public ResponseEntity<PiezaDTO> obtenerPiezaPorReferencia(@Parameter(description = "referencia de la pieza a buscar",
            required = true) @PathVariable String referencia) {

        return new ResponseEntity<>(piezaService.findByReferencia(referencia), HttpStatus.OK);
    }

    @Operation(summary = "Obtener Piezas por nombre", description = "Obtener Piezas por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Piezas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    })
    })
    @GetMapping("/nombre/{nombre}")
    public List<PiezaDTO> obtenerPiezasPorNombre(@Parameter(description = "nombre de la pieza a buscar",
            required = true) @PathVariable String nombre) {

        return piezaService.findByNombre(nombre);
    }

    @Operation(summary = "Modificar una Pieza", description = "Modificar una Pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pieza modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping()
    public ResponseEntity<PiezaDTO> modificarPieza(@Valid @RequestBody PiezaDTO piezaDTO) {

        return new ResponseEntity<>(piezaService.modificarPieza(piezaDTO), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar una pieza", description = "Eliminar una pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pieza eliminada correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pieza no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPieza(@Parameter(description = "id de la pieza", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(piezaService.deleteById(id), HttpStatus.OK);
    }
}

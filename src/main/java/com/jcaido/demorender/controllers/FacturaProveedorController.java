package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.jcaido.demorender.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.jcaido.demorender.services.facturaProveedor.FacturaProveedorService;
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
@RequestMapping("/api/facturaProveedor")
public class FacturaProveedorController {

    private final FacturaProveedorService facturaProveedorService;

    public FacturaProveedorController(FacturaProveedorService facturaProveedorService) {
        this.facturaProveedorService = facturaProveedorService;
    }

    @Operation(summary = "Crear una nueva factura de proveedor", description = "Crear una nueva factura de proveedor," +
            " formato de feha dd-mm-aaaa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "factura de proveedor creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Proveedor no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT El numero de factura ya existe para ese proveedor",
                    content = @Content),
    })
    @PostMapping("/{idProveedor}")
    public ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(@Valid @RequestBody FacturaProveedorCrearDTO facturaProveedorCrearDTO,
                                                                     @Parameter(description = "id del proveedor", required = true)
                                                                     @PathVariable Long idProveedor) {

        return new ResponseEntity<>(facturaProveedorService.crearFacturaProveedor(facturaProveedorCrearDTO, idProveedor), HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener una lista con todas las facturas de proveedor", description = "Obtener una lista con todas las facturas de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de proveedor obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorBusquedasDTO.class))
                    })
    })
    @GetMapping()
    public List<FacturaProveedorBusquedasDTO> obtenerTodasLasFacturasProveedor() {

        return facturaProveedorService.findAll();
    }


    @Operation(summary = "Obtener una factura de proveedor por id", description = "Obtener una factura de proveedor por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura de proveedor obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Factura de proveedor no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<FacturaProveedorBusquedasDTO> obtenerFacturaProveedorPorId(
            @Parameter(description = "id de la factura de proveedor", required = true)
            @PathVariable Long id) {

        return new ResponseEntity<>(facturaProveedorService.findById(id), HttpStatus.OK);
    }


    @Operation(summary = "Obtener una lista con todas las facturas de proveedor entre fechas", description = "Obtener una lista con todas las facturas de proveedor entre fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de proveedor obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorBusquedasDTO.class))
                    })
    })
    @GetMapping("/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedorEntreFechas(
            @Parameter(description = "fecha inicial, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @Parameter(description = "fecha final, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaProveedorService.obtenerFacturasProveedoresEntreFechas(fechaFacturaInicial, fechaFacturaFinal);
    }


    @Operation(summary = "Obtener una lista de facturas de proveedor por proveedor entre fechas", description = "Obtener una lista de facturas de proveedor por proveedor entre fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de proveedor obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorBusquedasDTO.class))
                    })
    })
    @GetMapping("/{idProveedor}/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasPorProveedorEntreFechas(
            @Parameter(description = "id del proveedor", required = true)
            @PathVariable Long idProveedor,
            @Parameter(description = "fecha inicial, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @Parameter(description = "fecha final, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaProveedorService.obtenerFacturasPorProveedorEntreFechas(idProveedor, fechaFacturaInicial, fechaFacturaFinal);
    }


    @Operation(summary = "Modificar una factura de proveedor", description = "Modificar una factura de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura de proveedor modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Factura de proveedor no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT [El proveedor asociado a la factura no existe]," +
                    " [El numero de factura ya existe para ese proveedor]",
                    content = @Content)
    })
    @PutMapping("/{idProveedor}")
    public ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(@Valid @RequestBody FacturaProveedorDTO facturaProveedorDTO,
                                                                         @Parameter(description = "id del proveedor", required = true)
                                                                         @PathVariable Long idProveedor) {

        return new ResponseEntity<>(facturaProveedorService.modificarFacturaProveedor(facturaProveedorDTO, idProveedor), HttpStatus.OK);
    }


    @Operation(summary = "Eliminar una factura de proveedor", description = "Eliminar una factura de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura de proveedor eliminada correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Factura de proveedor no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Existen albaranes asociados con esa factura de proveedor",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFacturaProveedor(
            @Parameter(description = "id de la factura de proveedor", required = true)
            @PathVariable Long id) {

        return new ResponseEntity<>(facturaProveedorService.deleteById(id), HttpStatus.OK);
    }
}

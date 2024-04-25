package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.jcaido.demorender.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.jcaido.demorender.services.albaranProveedor.AlbaranProveedorService;
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
@RequestMapping("/api/albaranProveedor")
public class AlbaranProveedorController {

    private final AlbaranProveedorService albaranProveedorService;

    public AlbaranProveedorController(AlbaranProveedorService albaranProveedorService) {
        this.albaranProveedorService = albaranProveedorService;
    }

    @Operation(summary = "Crear un nuevo albarán de proveedor", description = "Crear un nuevo albarán de proveedor, " +
            " formato de fecha dd-mm-aaaa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "albarán de proveedor creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT [El proveedor asociado al albarán no existe], [El numero de albaran ya existe para ese proveedor]",
                    content = @Content),
    })
    @PostMapping("/{idProveedor}")
    public ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(@Valid @RequestBody AlbaranProveedorCrearDTO albaranProveedorCrearDTO
            , @Parameter(description = "id del proveedor", required = true) @PathVariable Long idProveedor) {

        return new ResponseEntity<>(albaranProveedorService.crearAlbaranProveedor(albaranProveedorCrearDTO, idProveedor), HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener una lista con todos los albaranes de proveedor", description = "Obtener una lista con todos los albaranes de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albaranes de proveedor obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorBusquedasDTO.class))
                    })
    })
    @GetMapping
    public List<AlbaranProveedorBusquedasDTO> obtenerTodosLosAlbaranesProveedor() {

        return albaranProveedorService.findAll();
    }


    @Operation(summary = "Obtener una lista con todos los albaranes de proveedor", description = "solo se incluye en la lista el id, la fecha, el numero, el nombre y el cif/nif del proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albaranes de proveedor obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/parcial")
    public List<AlbaranProveedorBusquedasParcialDTO> obtenerTodosLosAlbaranesProveedorParcial() {

        return albaranProveedorService.findAllParcial();
    }


    @Operation(summary = "Obtener albarán de proveedor por id", description = "Obtener albarán de proveedor por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albarán de proveedor obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Albarán de proveedor no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlbaranProveedorBusquedasDTO> obtenerAlbaranProveedorPorId(@Parameter(description = "id del albarán de proveedor a buscar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(albaranProveedorService.findById(id), HttpStatus.OK);
    }


    @Operation(summary = "Obtener una lista de albaranes de proveedor no facturados", description = "Obtener una lista de albaranes de proveedor no facturados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albaranes de proveedor no facturados obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorBusquedasDTO.class))
                    }),
    })
    @GetMapping("/no-facturados/{idProveedor}")
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorNoFacturados(@Parameter(description = "id del proveedor",
            required = true) @PathVariable Long idProveedor) {

        return albaranProveedorService.obtenerAlbaranesPtesFacturarPorProveedorHQL(idProveedor);
    }


    @Operation(summary = "Obtener una lista de albaranes asignados a una factura de proveedor", description = "Obtener una lista de albaranes asignados a una factura de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albaranes asignados a factura de proveedor obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorBusquedasDTO.class))
                    }),
    })
    @GetMapping("/factura/{idFactura}")
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesPorFacturaProveedor(@Parameter(description = "id de la factura del proveedor",
            required = true) @PathVariable Long idFactura) {

        return albaranProveedorService.obtenerAlbaranesProveedorPorFacturaProveedorHQL(idFactura);
    }


    @Operation(summary = "Modificar un albarán de proveedor", description = "Modificar un albarán de proveedor, " +
            " formato de fecha dd-mm-aaaa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albarán de proveedor modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Albarán de proveedor no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "CONFLICT [El proveedor asociado al albarán no existe]," +
                    " [El numero de albaran ya existe para ese proveedor]",
                    content = @Content)
    })
    @PutMapping("/{idProveedor}")
    public ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(@Parameter(description = "id del proveedor",
            required = true) @Valid @RequestBody AlbaranProveedorDTO albaranProveedorDTO, @PathVariable Long idProveedor) {

        return new ResponseEntity<>(albaranProveedorService.modificarAlbaranProveedor(albaranProveedorDTO, idProveedor), HttpStatus.OK);
    }


    @Operation(summary = "Facturar un albarán de proveedor", description = "Facturar un albarán de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albarán de proveedor facturado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "[Albarán de proveedor no encontrado], [Factura de proveedor no encontrada]",
                    content = @Content)
    })
    @PutMapping("/facturarAlbaran/{idAlbaran}/{idFactura}")
    public ResponseEntity<AlbaranProveedorDTO> facturarAlbaranProveedor(@Parameter(description = "id del albarán de proveedor",
            required = true) @PathVariable Long idAlbaran, @Parameter(description = "id de la factura de proveedor",
            required = true) @PathVariable Long idFactura) {

        return new ResponseEntity<>(albaranProveedorService.facturarAlbaranProveedor(idAlbaran, idFactura), HttpStatus.OK);
    }


    @Operation(summary = "Marcar un albarán ya facturado como no facturado", description = "Marcar un albarán ya facturado como no facturado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albarán de proveedor modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = AlbaranProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Albarán de proveedor no encontrado",
                    content = @Content)
    })
    @PutMapping("/noFacturarAlbaran/{idAlbaran}")
    public ResponseEntity<AlbaranProveedorDTO> noFacturarAlbaranProveedorFacturado(@Parameter(description = "id del albarán de proveedor",
            required = true) @PathVariable Long idAlbaran) {

        return new ResponseEntity<>(albaranProveedorService.noFacturarAlbaranProveedorFacturado(idAlbaran), HttpStatus.OK);
    }


    @Operation(summary = "Eliminar un albarán de proveedor", description = "Eliminar un albarán de proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Albarán de proveedor eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Albarán de proveedor no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "[Existen entradas de piezas asociadas con ese albaran de proveedor]," +
                    " [El albarán ya está asociado a una factura]",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlbaranProveedor(@Parameter(description = "id del albarán de proveedor",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(albaranProveedorService.deleteById(id), HttpStatus.OK);
    }
}

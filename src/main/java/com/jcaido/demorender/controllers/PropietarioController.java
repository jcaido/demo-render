package com.jcaido.demorender.controllers;

import com.jcaido.demorender.DTOs.propietario.PropietarioBusquedasDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioCrearDTO;
import com.jcaido.demorender.DTOs.propietario.PropietarioDTO;
import com.jcaido.demorender.services.propietario.PropietarioService;
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
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @Operation(summary = "Crear un nuevo Propietario", description = "Crear un nuevo Propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "propietario creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping("/{id_codigoPostal}")
    public ResponseEntity<PropietarioDTO> crearPropietario(@Valid @RequestBody PropietarioCrearDTO propietarioCrearDTO
            , @Parameter(description = "id del código postal del propietario", required = true) @PathVariable Long id_codigoPostal) {

        return new ResponseEntity<>(propietarioService.crearPropietario(propietarioCrearDTO, id_codigoPostal), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los Propietarios", description = "Obtener todos los Propietarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    })
    })
    @GetMapping()
    public List<PropietarioBusquedasDTO> obtenerTodosLosPropietarios() {

        return propietarioService.findAll();
    }

    @Operation(summary = "Obtener todos los Propietarios", description = "Obtener todos los Propietarios sin incluir todos los campos" +
            "solo incluimos los campos id, nombre, primer apellido, segundo apellido y dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/parcial")
    public List<PropietarioBusquedasParcialDTO> obtenerTodosLosPropietariosParcial() {

        return propietarioService.findAllPartial();
    }

    @Operation(summary = "Obtener Propietario por id", description = "Obtener Propietario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "propietario no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PropietarioBusquedasDTO> obtenerPropietarioPorId(@Parameter(description = "id del propietario a buscar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(propietarioService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener Propietario por dni", description = "Obtener Propietario por dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "propietario no encontrado",
                    content = @Content)
    })
    @GetMapping("/dni/{dni}")
    public ResponseEntity<PropietarioBusquedasDTO> obtenerPropietarioPorDni(@Parameter(description = "dni del propietario a buscar",
            required = true) @PathVariable String dni) {

        return new ResponseEntity<>(propietarioService.findByDni(dni), HttpStatus.OK);
    }

    @Operation(summary = "Obtener Propietarios por nombre", description = "Obtener Propietarios por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    })
    })
    @GetMapping("/nombre/{nombre}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorNombre(@Parameter(description = "nombre del propietario a buscar",
            required = true) @PathVariable String nombre) {

        return propietarioService.findByNombre(nombre);
    }

    @Operation(summary = "Obtener Propietarios por primer apellido", description = "Obtener Propietarios por primer apellido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    })
    })
    @GetMapping("/primer-apellido/{primerApellido}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorPrimerApellido(@Parameter(description = "primer apellido del propietario a buscar",
            required = true) @PathVariable String primerApellido) {

        return propietarioService.findByPrimerApellido(primerApellido);
    }

    @Operation(summary = "Obtener Propietarios por primer apellido", description = "Obtener Propietarios por primer apellido" +
            "sin incluir todos lo campos, solo id, nombre, primer apellido, segundo apellido y dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/primer-apellido/parcial/{primerApellido}")
    public List<PropietarioBusquedasParcialDTO> obtenerTodosLosPropietariosPorPrimrApellidoParcial(@Parameter(description = "primer apellido del propietario a buscar",
            required = true) @PathVariable String primerApellido) {

        return propietarioService.findByPrimerApellidoPartial(primerApellido);
    }

    @Operation(summary = "Obtener Propietarios por nombre mas apellidos", description = "Obtener Propietarios por nombre mas apellidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    })
    })
    @GetMapping("/nombre-apellidos/{nombre}-{primerApellido}-{segundoApellido}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorNombreMasApellidos(
            @Parameter(description = "nombre del propietario a buscar", required = true)
            @PathVariable String nombre,
            @Parameter(description = "primer apellido del propietario a buscar", required = true)
            @PathVariable String primerApellido,
            @Parameter(description = "segundo apellido del propietario a buscar", required = true)
            @PathVariable String segundoApellido)
    {

        return propietarioService.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, primerApellido, segundoApellido);
    }

    @Operation(summary = "Obtener Propietarios por codigo postal", description = "Obtener Propietarios por codigo postal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasDTO.class))
                    })
    })
    @GetMapping("/codigo_postal/{id}")
    public List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostal(@Parameter(description = "id del codigo postal",
            required = true) @PathVariable Long id) {

        return propietarioService.obtenerPropietariosPorCodigoPostalSQL(id);

    }

    @Operation(summary = "Obtener Propietarios por codigo postal", description = "Obtener Propietarios por codigo postal sin incluir" +
            "todos los campos, solo incluimos el id, nombre, primer apellido, segundo apellido y el dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/codigo_postal/parcial/{id}")
    public List<PropietarioBusquedasParcialDTO> obtenerPropietariosPorCodigoPostalParcial(@Parameter(description = "id del codigo postal",
            required = true) @PathVariable Long id) {

        return propietarioService.obtenerPropietariosPorCodigoPostalParcial(id);

    }

    @Operation(summary = "Modificar un Propietario", description = "Modificar un Propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PropietarioDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping("/{id_codigoPostal}")
    public ResponseEntity<PropietarioDTO> modificarPropietario(@Valid @RequestBody PropietarioDTO propietarioDTO
            , @Parameter(description = "id del codigo postal", required = true) @PathVariable Long id_codigoPostal) {

        return new ResponseEntity<>(propietarioService.modificarPropietario(propietarioDTO, id_codigoPostal), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un Propietario", description = "Eliminar un Propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propietario eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Propietario no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPropietario(@Parameter(description = "id del propietario", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(propietarioService.deleteById(id), HttpStatus.OK);
    }
}

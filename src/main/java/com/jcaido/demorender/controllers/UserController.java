package com.jcaido.demorender.controllers;

import com.jcaido.demorender.models.User;
import com.jcaido.demorender.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "Obtener todos los usuarios", description = "Obtener todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    })
    })
    @GetMapping()
    public List<User> gettAllUsers() {
        return userRepository.findAll();
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crear un nuevo Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "código postal creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping()
    public ResponseEntity<User> newUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }
}

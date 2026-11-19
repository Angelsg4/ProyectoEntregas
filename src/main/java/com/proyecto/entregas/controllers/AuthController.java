package com.proyecto.entregas.controllers;

import com.proyecto.entregas.dtos.RegistroUsuarioDTO;
import com.proyecto.entregas.dtos.UsuarioResponseDTO;
import com.proyecto.entregas.services.UsuarioService;
import com.proyecto.entregas.dtos.AuthResponseDTO;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.entregas.dtos.LoginDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(
            @Valid @RequestBody RegistroUsuarioDTO dto) {

        UsuarioResponseDTO usuario =
                usuarioService.registrarCliente(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuario);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
        @Valid @RequestBody LoginDTO dto) {

    AuthResponseDTO respuesta =
            usuarioService.iniciarSesion(dto);

    return ResponseEntity.ok(respuesta);
    }
}
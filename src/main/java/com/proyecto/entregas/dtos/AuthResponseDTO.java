package com.proyecto.entregas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String tipo;
    private UsuarioResponseDTO usuario;
}
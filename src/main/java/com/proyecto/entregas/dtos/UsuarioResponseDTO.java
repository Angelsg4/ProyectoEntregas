package com.proyecto.entregas.dtos;

import com.proyecto.entregas.enums.Rol;
import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;
}
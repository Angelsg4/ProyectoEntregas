package com.proyecto.entregas.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {
    private Long id;
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private String codigoPostal;
}
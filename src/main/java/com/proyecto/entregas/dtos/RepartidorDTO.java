package com.proyecto.entregas.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepartidorDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String vehiculo;
}
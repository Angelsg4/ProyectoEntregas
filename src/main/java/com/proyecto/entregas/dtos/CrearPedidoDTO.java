package com.proyecto.entregas.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoDTO {
    private String descripcion;
    private DireccionDTO destino;
    private Long repartidorId; 
}
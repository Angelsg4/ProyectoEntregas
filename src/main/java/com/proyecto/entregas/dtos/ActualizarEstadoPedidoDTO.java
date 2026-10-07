package com.proyecto.entregas.dtos;

import com.proyecto.entregas.enums.EstadoPedido;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoPedidoDTO {
    private EstadoPedido nuevoEstado;
    private Long repartidorId; 
}
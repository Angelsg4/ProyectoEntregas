package com.proyecto.entregas.dtos;

import com.proyecto.entregas.enums.EstadoPedido;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private String descripcion;
    private EstadoPedido estado;
    private DireccionDTO destino;
    private RepartidorDTO repartidor;
}
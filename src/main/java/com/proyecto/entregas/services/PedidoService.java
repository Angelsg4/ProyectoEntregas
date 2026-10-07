package com.proyecto.entregas.services;

import com.proyecto.entregas.dtos.*;
import com.proyecto.entregas.enums.EstadoPedido;
import com.proyecto.entregas.models.Direccion;
import com.proyecto.entregas.models.Pedido;
import com.proyecto.entregas.models.Repartidor;
import com.proyecto.entregas.repositories.RepositorioPedido;
import com.proyecto.entregas.repositories.RepositorioRepartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private RepositorioPedido pedidoRepository;

    @Autowired
    private RepositorioRepartidor repartidorRepository;

    @Autowired
    private DireccionService direccionService;

    public List<PedidoResponseDTO> obtenerTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO obtenerPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return convertirAResponseDTO(pedido);
    }

    public PedidoResponseDTO crear(CrearPedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setDescripcion(dto.getDescripcion());
        pedido.setEstado(EstadoPedido.ASIGNADO);

        if (dto.getDestino() != null) {
            Direccion direccion = direccionService.convertirAEntidad(dto.getDestino());
            pedido.setDestino(direccion);
        }

        if (dto.getRepartidorId() != null) {
            Repartidor repartidor = repartidorRepository.findById(dto.getRepartidorId())
                    .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con ID: " + dto.getRepartidorId()));
            pedido.setRepartidor(repartidor);
        }

        Pedido guardado = pedidoRepository.save(pedido);
        return convertirAResponseDTO(guardado);
    }

    public PedidoResponseDTO actualizarEstado(Long id, ActualizarEstadoPedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        if (dto.getNuevoEstado() != null) {
            pedido.setEstado(dto.getNuevoEstado());
        }

        if (dto.getRepartidorId() != null) {
            Repartidor repartidor = repartidorRepository.findById(dto.getRepartidorId())
                    .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con ID: " + dto.getRepartidorId()));
            pedido.setRepartidor(repartidor);
        }

        Pedido actualizado = pedidoRepository.save(pedido);
        return convertirAResponseDTO(actualizado);
    }

    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO convertirAResponseDTO(Pedido pedido) {
        DireccionDTO direccionDTO = direccionService.convertirADTO(pedido.getDestino());
        
        RepartidorDTO repartidorDTO = null;
        if (pedido.getRepartidor() != null) {
            repartidorDTO = new RepartidorDTO(
                    pedido.getRepartidor().getId(),
                    pedido.getRepartidor().getNombre(),
                    pedido.getRepartidor().getTelefono(),
                    pedido.getRepartidor().getVehiculo()
            );
        }

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getDescripcion(),
                pedido.getEstado(),
                direccionDTO,
                repartidorDTO
        );
    }
}

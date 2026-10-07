package com.proyecto.entregas.services;

import com.proyecto.entregas.dtos.RepartidorDTO;
import com.proyecto.entregas.models.Repartidor;
import com.proyecto.entregas.repositories.RepositorioRepartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepartidorService {

    @Autowired
    private RepositorioRepartidor repartidorRepository;

    public List<RepartidorDTO> obtenerTodos() {
        return repartidorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public RepartidorDTO obtenerPorId(Long id) {
        Repartidor repartidor = repartidorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con ID: " + id));
        return convertirADTO(repartidor);
    }

    public RepartidorDTO crear(RepartidorDTO dto) {
        Repartidor repartidor = new Repartidor();
        repartidor.setNombre(dto.getNombre());
        repartidor.setTelefono(dto.getTelefono());
        repartidor.setVehiculo(dto.getVehiculo());

        Repartidor guardado = repartidorRepository.save(repartidor);
        return convertirADTO(guardado);
    }

    public RepartidorDTO actualizar(Long id, RepartidorDTO dto) {
        Repartidor repartidor = repartidorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repartidor no encontrado con ID: " + id));

        repartidor.setNombre(dto.getNombre());
        repartidor.setTelefono(dto.getTelefono());
        repartidor.setVehiculo(dto.getVehiculo());

        Repartidor actualizado = repartidorRepository.save(repartidor);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        repartidorRepository.deleteById(id);
    }

    private RepartidorDTO convertirADTO(Repartidor repartidor) {
        if (repartidor == null) return null;
        return new RepartidorDTO(
                repartidor.getId(),
                repartidor.getNombre(),
                repartidor.getTelefono(),
                repartidor.getVehiculo()
        );
    }
}
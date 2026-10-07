package com.proyecto.entregas.services;

import com.proyecto.entregas.dtos.DireccionDTO;
import com.proyecto.entregas.models.Direccion;
import com.proyecto.entregas.repositories.RepositorioDireccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DireccionService {

    @Autowired
    private RepositorioDireccion direccionRepository;

    public List<DireccionDTO> obtenerTodas() {
        return direccionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public DireccionDTO obtenerPorId(Long id) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + id));
        return convertirADTO(direccion);
    }

    public DireccionDTO crear(DireccionDTO dto) {
        Direccion direccion = convertirAEntidad(dto);
        Direccion guardada = direccionRepository.save(direccion);
        return convertirADTO(guardada);
    }

    public DireccionDTO actualizar(Long id, DireccionDTO dto) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada con ID: " + id));

        direccion.setCalle(dto.getCalle());
        direccion.setNumero(dto.getNumero());
        direccion.setColonia(dto.getColonia());
        direccion.setCiudad(dto.getCiudad());
        direccion.setCodigoPostal(dto.getCodigoPostal());

        Direccion actualizada = direccionRepository.save(direccion);
        return convertirADTO(actualizada);
    }

    public void eliminar(Long id) {
        direccionRepository.deleteById(id);
    }

    public DireccionDTO convertirADTO(Direccion direccion) {
        if (direccion == null) return null;
        return new DireccionDTO(
                direccion.getId(),
                direccion.getCalle(),
                direccion.getNumero(),
                direccion.getColonia(),
                direccion.getCiudad(),
                direccion.getCodigoPostal()
        );
    }

    public Direccion convertirAEntidad(DireccionDTO dto) {
        if (dto == null) return null;
        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getCalle());
        direccion.setNumero(dto.getNumero());
        direccion.setColonia(dto.getColonia());
        direccion.setCiudad(dto.getCiudad());
        direccion.setCodigoPostal(dto.getCodigoPostal());
        return direccion;
    }
}
package com.proyecto.entregas.controllers;

import com.proyecto.entregas.dtos.RepartidorDTO;
import com.proyecto.entregas.services.RepartidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repartidores")
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @GetMapping
    public ResponseEntity<List<RepartidorDTO>> obtenerTodos() {
        return ResponseEntity.ok(repartidorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepartidorDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repartidorService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RepartidorDTO> crear(@RequestBody RepartidorDTO dto) {
        return new ResponseEntity<>(repartidorService.crear(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepartidorDTO> actualizar(@PathVariable Long id, @RequestBody RepartidorDTO dto) {
        return ResponseEntity.ok(repartidorService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repartidorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
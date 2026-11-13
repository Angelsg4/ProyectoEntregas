package com.proyecto.entregas.repositories;

import com.proyecto.entregas.models.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioDireccion
        extends JpaRepository<Direccion, Long> {
}
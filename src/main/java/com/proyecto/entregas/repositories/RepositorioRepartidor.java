package com.proyecto.entregas.repositories;

import com.proyecto.entregas.models.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRepartidor
        extends JpaRepository<Repartidor, Long> {
}
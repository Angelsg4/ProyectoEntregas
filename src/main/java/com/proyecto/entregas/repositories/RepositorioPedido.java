package com.proyecto.entregas.repositories;

import com.proyecto.entregas.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPedido
        extends JpaRepository<Pedido, Long> {
}
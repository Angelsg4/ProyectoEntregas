package com.proyecto.entregas.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "repartidores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repartidor {
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String telefono;
    private String vehiculo;
}
package com.proyecto.entregas.dtos;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDTO {

    private Long id;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    @NotBlank(message = "El número es obligatorio")
    private String numero;

    @NotBlank(message = "La colonia es obligatoria")
    private String colonia;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "El código postal es obligatorio")
    private String codigoPostal;
}
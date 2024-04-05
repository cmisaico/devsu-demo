package com.devsu.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimientoRequest {
    @NotNull(message = "La cuenta es obligatoria")
    private Long cuentaId;
    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    private String tipo;
    @Digits(integer = 10, fraction = 2, message = "El valor debe tener máximo 10 enteros y 2 decimales")
    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "El formato de la fecha debe ser dd/MM/YYYY")
    private String fecha;
}

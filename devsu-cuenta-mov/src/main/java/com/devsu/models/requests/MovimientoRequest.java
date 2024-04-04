package com.devsu.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovimientoRequest {
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener 6 caracteres")
    @NotNull(message = "El número de cuenta no puede ser nulo")
    private String numeroCuenta;
    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    private String tipo;
    @Digits(integer = 10, fraction = 2, message = "El valor debe tener máximo 10 enteros y 2 decimales")
    @NotNull(message = "El valor no puede ser nulo")
    private Double valor;
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "El formato de la fecha debe ser dd/MM/YYYY")
    private String fecha;
}

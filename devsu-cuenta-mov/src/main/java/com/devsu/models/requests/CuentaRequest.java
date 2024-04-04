package com.devsu.models.requests;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CuentaRequest {
    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener 6 caracteres")
    private String numero;
    private String tipo;
    @Digits(integer = 10, fraction = 2, message = "El valor debe tener máximo 10 enteros y 2 decimales")
    private Double saldoInicial;
    private Boolean estado;
}

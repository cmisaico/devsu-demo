package com.devsu.models.responses;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CuentaResponse {
    private Long id;
    private String numero;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Long clienteId;
}

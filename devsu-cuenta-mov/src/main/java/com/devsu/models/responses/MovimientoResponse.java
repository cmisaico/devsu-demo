package com.devsu.models.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoResponse {
    private Long id;
    private LocalDate fecha;
    private LocalDateTime createdAt;
    private String tipo;
    private Double valor;
    private Double saldo;
}

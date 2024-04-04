package com.devsu.models;

import lombok.Data;

@Data
public class Movimiento {
    private String id;
    private String fecha;
    private String tipo;
    private String saldo;
}

package com.devsu.models;

import lombok.Data;

@Data
public class Cuenta {
    private String id;
    private String numeroCuenta;
    private String tipoCuenta;
    private String saldoInicial;
    private String estado;

}

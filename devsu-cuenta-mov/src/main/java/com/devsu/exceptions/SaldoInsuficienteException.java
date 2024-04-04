package com.devsu.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Saldo insuciente";
    }
}

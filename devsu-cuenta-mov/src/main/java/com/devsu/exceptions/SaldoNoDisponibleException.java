package com.devsu.exceptions;

public class SaldoNoDisponibleException extends RuntimeException {
    public SaldoNoDisponibleException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Saldo no disponible";
    }
}

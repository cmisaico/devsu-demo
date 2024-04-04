package com.devsu.exceptions;

public class CuentaNoExisteException extends RuntimeException {
    public CuentaNoExisteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Cuenta no existe en la base de datos";
    }
}

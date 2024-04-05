package com.devsu.exceptions;

public class MovimientoNoExisteException extends RuntimeException {
    public MovimientoNoExisteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Movimiento no existe en la base de datos";
    }
}

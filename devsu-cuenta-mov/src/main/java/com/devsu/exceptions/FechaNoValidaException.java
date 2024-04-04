package com.devsu.exceptions;

public class FechaNoValidaException extends RuntimeException {
    public FechaNoValidaException() {
        super();
    }

    @Override
    public String getMessage() {
        return "La fecha debe ser mayor a la fecha del ultimo movimiento";
    }
}

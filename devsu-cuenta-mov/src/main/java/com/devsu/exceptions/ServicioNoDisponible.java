package com.devsu.exceptions;

public class ServicioNoDisponible extends RuntimeException {
    public ServicioNoDisponible() {
        super();
    }

    @Override
    public String getMessage() {
        return "Servicio no disponible, intentelo mas tarde";
    }
}

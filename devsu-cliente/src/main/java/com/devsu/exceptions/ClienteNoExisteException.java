package com.devsu.exceptions;

public class ClienteNoExisteException extends RuntimeException {
    public ClienteNoExisteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Cliente no existe en la base de datos";
    }
}

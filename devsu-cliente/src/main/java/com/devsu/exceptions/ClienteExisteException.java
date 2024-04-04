package com.devsu.exceptions;

public class ClienteExisteException extends RuntimeException {
    public ClienteExisteException() {
        super();
    }

    @Override
    public String getMessage() {
        return "El numero de identificacion ya existe";
    }
}

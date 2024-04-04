package com.devsu.models;

import lombok.Data;

@Data
public class Persona {
    private String id;
    private String nombre;
    private String genero;
    private String edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}

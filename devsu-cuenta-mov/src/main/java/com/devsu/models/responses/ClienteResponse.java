package com.devsu.models.responses;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String contrasenia;
    private Boolean estado;
}

package com.devsu.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Cliente extends Persona {
    private String id;
    private String contrasenia;
    private String estado;
}

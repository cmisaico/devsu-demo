package com.devsu.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 8, max = 50, message = "El nombre debe tener entre 8 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El genero es obligatorio")
    @Size(min = 1, max = 1, message = "El genero debe tener 1 caracter")
    private String genero;

    @NotNull(message = "El genero es obligatorio")
    @Min(value = 18, message = "La edad debe ser mayor a 18")
    private Integer edad;

    @NotBlank(message = "La identificacion es obligatoria")
    @Size(min = 8, max = 8, message = "La identificacion debe tener 8 caracteres")
    private String identificacion;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    @NotBlank(message = "La contrasenia es obligatorio")
    private String contrasenia;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

}

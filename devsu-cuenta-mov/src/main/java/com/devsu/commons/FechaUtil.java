package com.devsu.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FechaUtil {
    private FechaUtil() {}
    public static LocalDate parsearFecha(String fecha) {
        return LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

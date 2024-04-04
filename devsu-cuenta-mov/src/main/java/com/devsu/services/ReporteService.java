package com.devsu.services;

import com.devsu.models.responses.ReporteResponse;
import reactor.core.publisher.Flux;

public interface ReporteService {

    public Flux<ReporteResponse> obtenerReporte(String fechaInicio, String fechaFin, Long clienteId);

}

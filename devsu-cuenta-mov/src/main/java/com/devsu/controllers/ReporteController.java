package com.devsu.controllers;

import com.devsu.commons.valids.Fecha;
import com.devsu.models.responses.ReporteResponse;
import com.devsu.services.ReporteService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reportes")
@Validated
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/")
    public Flux<ReporteResponse> obtenerReporte(
            @RequestParam("fechaInicio") @Fecha String fechaInicio,
            @RequestParam("fechaFin") @Fecha String fechaFin,
            @RequestParam("clienteId") Long clienteId) {
        return reporteService.obtenerReporte(fechaInicio, fechaFin, clienteId)
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }
}

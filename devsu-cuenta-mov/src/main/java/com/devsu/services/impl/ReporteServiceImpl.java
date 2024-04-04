package com.devsu.services.impl;

import com.devsu.commons.FechaUtil;
import com.devsu.exceptions.ClienteNoExisteException;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.models.responses.ReporteResponse;
import com.devsu.repositories.ReporteRepository;
import com.devsu.services.ReporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServiceImpl implements ReporteService {
    private final WebClient webClient;
    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(WebClient webClient, ReporteRepository reporteRepository) {
        this.webClient = webClient;
        this.reporteRepository = reporteRepository;
    }

    @Override
    public Flux<ReporteResponse> obtenerReporte(String fechaInicio, String fechaFin, Long clienteId) {
        LocalDate fechaInicioTemp = FechaUtil.parsearFecha(fechaInicio);
        LocalDate fechaFinTemp = FechaUtil.parsearFecha(fechaFin);
        Map<String, Object> params = new HashMap<>();
        params.put("id", clienteId);
        return webClient.get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NO_CONTENT,
                        response -> Mono.error(new ClienteNoExisteException()))
                .bodyToFlux(ClienteResponse.class)
                .flatMap(cliente -> {
                    List<ReporteResponse> reporte = reporteRepository
                            .getReporteByNumeroCuenta(fechaInicioTemp, fechaFinTemp, cliente.getId());
                    reporte.forEach(r -> r.setCliente(cliente.getNombre()));
                    return Flux.fromIterable(reporte);
                })
                .onErrorResume(Mono::error);
    }
}

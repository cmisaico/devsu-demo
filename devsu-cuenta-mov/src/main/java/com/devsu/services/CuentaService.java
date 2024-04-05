package com.devsu.services;

import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.responses.CuentaResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CuentaService {
    Mono<Void> crear(CuentaRequest cuentaRequest);
    Mono<Void> actualizar(CuentaRequest cuentaRequest, Long id);
    Mono<Void> eliminar(Long id);
    Flux<CuentaResponse> listar();
    Mono<CuentaResponse> obtener(Long id);

}

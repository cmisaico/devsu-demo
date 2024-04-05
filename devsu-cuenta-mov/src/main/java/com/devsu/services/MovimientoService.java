package com.devsu.services;

import com.devsu.models.requests.MovimientoRequest;
import com.devsu.models.responses.MovimientoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovimientoService {

    Mono<Void> crear(MovimientoRequest movimientoRequest);
    Mono<Void> actualizar(MovimientoRequest movimientoRequest, Long id);
    Mono<Void> eliminar(Long id);
    Flux<MovimientoResponse> listar();
    Mono<MovimientoResponse> obtener(Long id);

}

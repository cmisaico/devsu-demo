package com.devsu.services;

import com.devsu.models.requests.MovimientoRequest;
import reactor.core.publisher.Mono;

public interface MovimientoService {

    Mono<Void> crear(MovimientoRequest movimientoRequest);

}

package com.devsu.services;

import com.devsu.models.requests.ClienteRequest;
import com.devsu.models.responses.ClienteResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClienteService {

    Mono<Void> crear(ClienteRequest clienteRequest);
    Mono<Void> actualizar(ClienteRequest clienteRequest, Long id);
    Mono<Void> eliminar(Long id);
    Flux<ClienteResponse> listar();
    Mono<ClienteResponse> obtener(Long id);
    Mono<ClienteResponse> obtenerByIdentificacion(String id);

}

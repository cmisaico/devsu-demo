package com.devsu.controllers;

import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.requests.MovimientoRequest;
import com.devsu.models.responses.CuentaResponse;
import com.devsu.models.responses.MovimientoResponse;
import com.devsu.services.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> crear(
            @Valid @RequestBody MovimientoRequest movimientoRequest) {
        return movimientoService.crear(movimientoRequest);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovimientoResponse> obtener(@PathVariable(required = false) Long id) {
        return movimientoService.obtener(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovimientoResponse> listar() {
        return movimientoService.listar()
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizar(@Valid @RequestBody MovimientoRequest movimientoRequest,
                                 @PathVariable(required = false) Long id) {
        return movimientoService.actualizar(movimientoRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable(required = false) Long id) {
        return movimientoService.eliminar(id);
    }
}
package com.devsu.controllers;

import com.devsu.models.requests.MovimientoRequest;
import com.devsu.services.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
}
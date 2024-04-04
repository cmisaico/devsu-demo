package com.devsu.controllers;

import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.models.responses.CuentaResponse;
import com.devsu.services.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> crear(
            @Valid @RequestBody CuentaRequest cuentaRequest) {
        return cuentaService.crear(cuentaRequest);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CuentaResponse> obtener(@PathVariable(required = false) Long id) {
        return cuentaService.obtener(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

}

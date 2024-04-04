package com.devsu.controllers;


import com.devsu.models.requests.ClienteRequest;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> crear(
            @Valid @RequestBody ClienteRequest clienteRequest) {
        return clienteService.crear(clienteRequest);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClienteResponse> obtener(@PathVariable(required = false) Long id) {
        return clienteService.obtener(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }
    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClienteResponse> listar() {
        return clienteService.listar()
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizar(@Valid @RequestBody ClienteRequest clienteRequest,
                                 @PathVariable(required = false) Long id) {
        return clienteService.actualizar(clienteRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable(required = false) Long id) {
        return clienteService.eliminar(id);
    }
}

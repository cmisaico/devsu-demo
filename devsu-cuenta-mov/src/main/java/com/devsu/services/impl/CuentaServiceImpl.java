package com.devsu.services.impl;

import com.devsu.exceptions.ClienteNoExisteException;
import com.devsu.mappers.CuentaMapper;
import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.models.responses.CuentaResponse;
import com.devsu.repositories.CuentaRepository;
import com.devsu.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private WebClient webClient;
    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Mono<Void> crear(CuentaRequest cuentaRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", cuentaRequest.getClienteId());
       return webClient.get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> {
                    System.out.println("Status: " + status);
                    return status == HttpStatus.NO_CONTENT;},
                        response -> {
                            System.out.println("Error al llamar con 2024 al API externa");
                            return Mono.error(new ClienteNoExisteException());
                        })
                .bodyToMono(ClienteResponse.class)
               .map(objeto -> {
                   if (objeto == null || objeto.getNombre() == null) {
                       System.out.println("El objeto no tiene contenido");
                       return Mono.error(new RuntimeException("El objeto no tiene contenido"));
                   }
                   System.out.println("El objeto tiene contenido" + objeto.toString());
                   return Mono.just(objeto);
               })
               .map(clienteResponse -> {
                   System.out.println("CuentaR  : " + cuentaRequest.getNumero());
                   System.out.println("CuentaR  : " + cuentaRequest.getTipo());
                   System.out.println("CuentaR  : " + cuentaRequest.getSaldoInicial());
                   return CuentaMapper.INSTANCE.cuentaRequestToCuentaEntidad(cuentaRequest);
               })
                .flatMap(cuentaEntity -> {
                    System.out.println("Cuenta  : " + cuentaEntity.getNumero());
                     cuentaRepository.save(cuentaEntity);
                     return Mono.empty();
                })
               .onErrorResume(e -> {
                   System.out.println("Error al llamar al API externa final");
                   return Mono.error(e);
               }).then();
    }

    @Override
    public Mono<Void> actualizar(CuentaRequest cuentaRequest) {
        return null;
    }

    @Override
    public Mono<Void> editar(CuentaRequest cuentaRequest) {
        return null;
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return null;
    }

    @Override
    public Flux<CuentaResponse> listar() {
        return null;
    }

    @Override
    public Mono<CuentaResponse> obtener(Long id) {
        return Mono.justOrEmpty(
                cuentaRepository.findById(id)
                        .map(CuentaMapper.INSTANCE::cuentaEntidadToCuentaResponse));

    }
}

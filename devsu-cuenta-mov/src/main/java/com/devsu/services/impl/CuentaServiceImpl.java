package com.devsu.services.impl;

import com.devsu.exceptions.ClienteNoExisteException;
import com.devsu.exceptions.CuentaNoExisteException;
import com.devsu.exceptions.ServicioNoDisponible;
import com.devsu.mappers.CuentaMapper;
import com.devsu.models.entities.CuentaEntity;
import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.models.responses.CuentaResponse;
import com.devsu.repositories.CuentaRepository;
import com.devsu.services.CuentaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final WebClient webClient;
    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(WebClient webClient, CuentaRepository cuentaRepository) {
        this.webClient = webClient;
        this.cuentaRepository = cuentaRepository;
    }


    @Override
    @CircuitBreaker(name = "cuentaService", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "limiteTiempo", fallbackMethod = "fallbackMethod")
    public Mono<Void> crear(CuentaRequest cuentaRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", cuentaRequest.getClienteId());
       return webClient.get()
                .uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NO_CONTENT,
                        response -> Mono.error(new ClienteNoExisteException()))
                .bodyToMono(ClienteResponse.class)
               .flatMap(o -> {
                   CuentaEntity ce = CuentaMapper.INSTANCE.toCuentaEntidad(cuentaRequest);
                   ce.setClienteId(o.getId());
                   return Mono.just(ce);
               })
                .flatMap(cuentaEntity -> cuentaRepository.findByNumero(cuentaEntity.getNumero())
                             .map(c -> Mono.<Void>error(new DataIntegrityViolationException("La cuenta ya existe")))
                             .orElseGet(() -> {
                                 cuentaRepository.save(cuentaEntity);
                                 return Mono.empty();
                             }))
                .onErrorResume(Mono::error);
    }

    Mono<Void> fallbackMethod(Throwable t) {
        if (t instanceof ClienteNoExisteException || t instanceof DataIntegrityViolationException ||
        t instanceof CuentaNoExisteException) {
            return Mono.error(t);
        }
        return Mono.error(new ServicioNoDisponible());
    }

    @Override
    public Mono<Void> actualizar(CuentaRequest cuentaRequest, Long id) {
        return obtener(id).map(o -> {
                    CuentaEntity cuentaEntity =
                            CuentaMapper.INSTANCE.toCuentaEntidad(cuentaRequest);
                    cuentaEntity.setId(o.getId());
                    return cuentaEntity;
                })
                .doOnNext(cuentaRepository::save)
                .switchIfEmpty(Mono.error(new CuentaNoExisteException()))
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> eliminar(Long id) {
        return obtener(id).map(CuentaResponse::getId)
                .doOnNext(cuentaRepository::deleteById)
                .switchIfEmpty(Mono.error(new CuentaNoExisteException()))
                .then();
    }

    @Override
    public Flux<CuentaResponse> listar() {
        return Flux.defer(() -> Flux.fromStream(
                cuentaRepository.findAll().stream()
                        .map(CuentaMapper.INSTANCE::toCuentaResponse))
        ).publishOn(Schedulers.parallel());
    }

    @Override
    public Mono<CuentaResponse> obtener(Long id) {
        return Mono.justOrEmpty(
                cuentaRepository.findById(id)
                        .map(CuentaMapper.INSTANCE::toCuentaResponse));

    }
}

package com.devsu.services.impl;


import com.devsu.commons.FechaUtil;
import com.devsu.exceptions.*;
import com.devsu.mappers.MovimientoMapper;
import com.devsu.models.entities.CuentaEntity;
import com.devsu.models.entities.MovimientoEntity;
import com.devsu.models.requests.MovimientoRequest;
import com.devsu.models.responses.MovimientoResponse;
import com.devsu.repositories.CuentaRepository;
import com.devsu.repositories.MovimientoRepository;
import com.devsu.services.MovimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    private final CuentaRepository cuentaRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    @Override
    public Mono<Void> crear(MovimientoRequest movimientoRequest) {
        Optional<CuentaEntity> cuentaEntity = cuentaRepository.findByNumero(movimientoRequest.getNumeroCuenta());
        if (cuentaEntity.isEmpty()) {
            return Mono.error(new CuentaNoExisteException());
        }
        Optional<MovimientoEntity> movimientoEntity = movimientoRepository
                .findFirstByCuentaNumeroOrderByCreatedAtDesc(movimientoRequest.getNumeroCuenta());

        if(movimientoEntity.isEmpty()){
            if(cuentaEntity.get().getSaldoInicial() < 0){
                return Mono.error(new SaldoNoDisponibleException());
            } else if(cuentaEntity.get().getSaldoInicial() < Math.abs(movimientoRequest.getValor())){
                return Mono.error(new SaldoInsuficienteException());
            }
            MovimientoEntity movimientoEntity1 = MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
            movimientoEntity1.setSaldo(cuentaEntity.get().getSaldoInicial()+movimientoRequest.getValor());
            movimientoEntity1.setCuenta(cuentaEntity.get());
            movimientoRepository.save(movimientoEntity1);
        } else {
            int result = FechaUtil.parsearFecha(movimientoRequest.getFecha()).compareTo(movimientoEntity.get().getFecha());
            if(result < 0){
                return Mono.error(new FechaNoValidaException());
            }
            if(movimientoEntity.get().getSaldo() < 0){
                return Mono.error(new SaldoNoDisponibleException());
            } else if(movimientoEntity.get().getSaldo() < Math.abs(movimientoRequest.getValor())){
                return Mono.error(new SaldoInsuficienteException());
            }
            MovimientoEntity movimientoEntity1 = MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
            movimientoEntity1.setSaldo(movimientoEntity.get().getSaldo()+movimientoRequest.getValor());
            movimientoEntity1.setCuenta(cuentaEntity.get());
            movimientoRepository.save(movimientoEntity1);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> actualizar(MovimientoRequest movimientoRequest, Long id) {
        return obtener(id).map(o -> {
                    MovimientoEntity movimientoEntity =
                            MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
                    movimientoEntity.setId(o.getId());
                    return movimientoEntity;
                })
                .doOnNext(movimientoRepository::save)
                .switchIfEmpty(Mono.error(new MovimientoNoExisteException()))
                .then();
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return obtener(id).map(MovimientoResponse::getId)
                .doOnNext(cuentaRepository::deleteById)
                .switchIfEmpty(Mono.error(new MovimientoNoExisteException()))
                .then();
    }

    @Override
    public Flux<MovimientoResponse> listar() {
        return Flux.defer(() -> Flux.fromStream(
                movimientoRepository.findAll().stream()
                        .map(MovimientoMapper.INSTANCE::toMovimientoResponse))
        ).publishOn(Schedulers.parallel());
    }

    @Override
    public Mono<MovimientoResponse> obtener(Long id) {
        return Mono.justOrEmpty(
                movimientoRepository.findById(id)
                        .map(MovimientoMapper.INSTANCE::toMovimientoResponse));
    }
}

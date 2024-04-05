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
        Optional<CuentaEntity> cuentaEntity = cuentaRepository.findById(movimientoRequest.getCuentaId());
        if (cuentaEntity.isEmpty()) {
            return Mono.error(new CuentaNoExisteException());
        }
        Optional<MovimientoEntity> lastMov = movimientoRepository
                .findFirstByCuentaIdOrderByCreatedAtDesc(movimientoRequest.getCuentaId());

        if(lastMov.isEmpty()){
            if(cuentaEntity.get().getSaldoInicial() < 0){
                return Mono.error(new SaldoNoDisponibleException());
            } else if(movimientoRequest.getValor()<0 && cuentaEntity.get().getSaldoInicial() < Math.abs(movimientoRequest.getValor())){
                return Mono.error(new SaldoInsuficienteException());
            }
            MovimientoEntity me = MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
            me.setSaldo(cuentaEntity.get().getSaldoInicial()+movimientoRequest.getValor());
            me.setCuenta(cuentaEntity.get());
            movimientoRepository.save(me);
        } else {
            int result = FechaUtil.parsearFecha(movimientoRequest.getFecha()).compareTo(lastMov.get().getFecha());
            if(result < 0){
                return Mono.error(new FechaNoValidaException());
            }
            if(lastMov.get().getSaldo() < 0){
                return Mono.error(new SaldoNoDisponibleException());
            } else if(movimientoRequest.getValor()<0 && lastMov.get().getSaldo() < Math.abs(movimientoRequest.getValor())){
                return Mono.error(new SaldoInsuficienteException());
            }
            MovimientoEntity me = MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
            me.setSaldo(lastMov.get().getSaldo()+movimientoRequest.getValor());
            me.setCuenta(cuentaEntity.get());
            movimientoRepository.save(me);
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> actualizar(MovimientoRequest movimientoRequest, Long id) {
        return obtener(id).map(o -> {
                    MovimientoEntity me =
                            MovimientoMapper.INSTANCE.toMovimientoEntidad(movimientoRequest);
                    me.setId(o.getId());
                    me.setSaldo(o.getSaldo());
                    me.setCuenta(CuentaEntity.builder().id(movimientoRequest.getCuentaId()).build());
                    return me;
                })
                .doOnNext(movimientoRepository::save)
                .switchIfEmpty(Mono.error(new MovimientoNoExisteException()))
                .then();
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return obtener(id).map(MovimientoResponse::getId)
                .doOnNext(movimientoRepository::deleteById)
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

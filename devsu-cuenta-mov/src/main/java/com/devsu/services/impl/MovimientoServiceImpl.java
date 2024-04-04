package com.devsu.services.impl;


import com.devsu.commons.FechaUtil;
import com.devsu.exceptions.CuentaNoExisteException;
import com.devsu.exceptions.FechaNoValidaException;
import com.devsu.exceptions.SaldoInsuficienteException;
import com.devsu.exceptions.SaldoNoDisponibleException;
import com.devsu.mappers.MovimientoMapper;
import com.devsu.models.entities.CuentaEntity;
import com.devsu.models.entities.MovimientoEntity;
import com.devsu.models.requests.MovimientoRequest;
import com.devsu.repositories.CuentaRepository;
import com.devsu.repositories.MovimientoRepository;
import com.devsu.services.MovimientoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
            MovimientoEntity movimientoEntity1 = MovimientoMapper.INSTANCE.movimientoRequestToMovimientoEntidad(movimientoRequest);
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
            MovimientoEntity movimientoEntity1 = MovimientoMapper.INSTANCE.movimientoRequestToMovimientoEntidad(movimientoRequest);
            System.out.println("Valor 2: " + movimientoEntity.get().getSaldo());
            movimientoEntity1.setSaldo(movimientoEntity.get().getSaldo()+movimientoRequest.getValor());
            movimientoEntity1.setCuenta(cuentaEntity.get());
            movimientoRepository.save(movimientoEntity1);
        }
        return Mono.empty();
    }
}

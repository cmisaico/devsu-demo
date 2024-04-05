package com.devsu.services.impl;

import com.devsu.exceptions.ClienteExisteException;
import com.devsu.exceptions.ClienteNoExisteException;
import com.devsu.mappers.ClienteMapper;
import com.devsu.models.entities.ClienteEntity;
import com.devsu.models.requests.ClienteRequest;
import com.devsu.models.responses.ClienteResponse;
import com.devsu.repositories.ClienteRepository;
import com.devsu.services.ClienteService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Mono<Void> crear(ClienteRequest clienteRequest) {
        return obtenerByIdentificacion(clienteRequest.getIdentificacion())
                .flatMap(o -> Mono.error(new ClienteExisteException()))
                .switchIfEmpty(Mono.defer(() -> {
                            ClienteEntity clienteEntityNuevo = ClienteMapper.INSTANCE
                                    .toClienteEntidad(clienteRequest);
                            return Mono.just(clienteRepository.save(clienteEntityNuevo));
                        })).then();
    }

    @Override
    public Mono<Void> actualizar(ClienteRequest clienteRequest, Long id) {
        return obtener(id).map(o -> {
            ClienteEntity clienteEntity =
                    ClienteMapper.INSTANCE.toClienteEntidad(clienteRequest);
            clienteEntity.setId(o.getId());
            return clienteEntity;
        })
                .doOnNext(clienteRepository::save)
                .switchIfEmpty(Mono.error(new ClienteNoExisteException()))
                .then();
    }

    @Override
    public Mono<Void> eliminar(Long id) {
        return obtener(id).map(ClienteResponse::getId)
                .doOnNext(clienteRepository::deleteById)
                .switchIfEmpty(Mono.error(new ClienteNoExisteException()))
                .then();
    }

    @Override
    public Flux<ClienteResponse> listar() {
        return Flux.defer(() -> Flux.fromStream(
                clienteRepository.findAll().stream()
                        .map(ClienteMapper.INSTANCE::toClienteResponse))
        ).publishOn(Schedulers.parallel());
    }

    @Override
    public Mono<ClienteResponse> obtener(Long id) {
       return Mono.justOrEmpty(
               clienteRepository.findById(id)
                       .map(ClienteMapper.INSTANCE::toClienteResponse));

    }

    @Override
    public Mono<ClienteResponse> obtenerByIdentificacion(String id) {
        return Mono.justOrEmpty(
                clienteRepository.findByIdentificacion(id)
                        .map(ClienteMapper.INSTANCE::toClienteResponse));
    }


}

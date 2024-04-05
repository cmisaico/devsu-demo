package com.devsu.mappers;

import com.devsu.models.entities.ClienteEntity;
import com.devsu.models.requests.ClienteRequest;
import com.devsu.models.responses.ClienteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", ignore = true)
    ClienteEntity toClienteEntidad(ClienteRequest clienteRequest);

    ClienteResponse toClienteResponse(ClienteEntity clienteEntidad);


}
package com.devsu.mappers;

import com.devsu.commons.FechaUtil;
import com.devsu.models.entities.MovimientoEntity;
import com.devsu.models.requests.MovimientoRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {
    MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuenta", ignore = true)
    @Mapping(target = "saldo", ignore = true)
    MovimientoEntity movimientoRequestToMovimientoEntidad(MovimientoRequest movimientoRequest);

    default LocalDate stringToLocalDate(String fechaString) {
        return FechaUtil.parsearFecha(fechaString);
    }

}
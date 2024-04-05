package com.devsu.mappers;

import com.devsu.models.entities.CuentaEntity;
import com.devsu.models.requests.CuentaRequest;
import com.devsu.models.responses.CuentaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    CuentaEntity toCuentaEntidad(CuentaRequest cuentaRequest);

    CuentaResponse toCuentaResponse(CuentaEntity cuentaEntidad);

}
package com.devsu.repositories;

import com.devsu.models.entities.MovimientoEntity;
import com.devsu.models.responses.ReporteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository  extends JpaRepository<MovimientoEntity, Long> {

    @Query("SELECT new com.devsu.models.responses.ReporteResponse(m.fecha, '', c.numero, c.tipo, c.saldoInicial, c.estado, m.valor, m.saldo) FROM MovimientoEntity m LEFT JOIN m.cuenta c WHERE m.fecha BETWEEN ?1 AND ?2 AND c.clienteId = ?3")
    List<ReporteResponse> getReporteByNumeroCuenta(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId);

}

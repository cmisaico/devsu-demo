package com.devsu.repositories;

import com.devsu.models.entities.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    public Optional<CuentaEntity> findByNumero(String numero);

}

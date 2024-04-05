package com.devsu.repositories;

import com.devsu.models.entities.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Long> {
    Optional<MovimientoEntity> findFirstByCuentaIdOrderByCreatedAtDesc(Long id);

}

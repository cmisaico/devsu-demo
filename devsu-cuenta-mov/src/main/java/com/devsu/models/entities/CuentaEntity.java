package com.devsu.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter
@Entity(name = "cuenta")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numero;
    private String tipo;
    private Double saldoInicial;
    private Boolean estado;
    private Long clienteId;
    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovimientoEntity> movimientos;
}

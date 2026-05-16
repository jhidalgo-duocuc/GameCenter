package cl.gamecenter.reporte.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "snapshot_ingreso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotIngresoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodo;
    private Integer totalSesiones;
    private Integer totalMembresias;
    private BigDecimal ingresosBrutos;
    private BigDecimal descuentosTotal;
    private BigDecimal ingresosNetos;
    private LocalDateTime generatedAt;
}

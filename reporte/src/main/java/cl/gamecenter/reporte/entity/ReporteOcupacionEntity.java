package cl.gamecenter.reporte.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reporte_ocupacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteOcupacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estacionId;
    private LocalDate fecha;
    private BigDecimal horasOcupadas;
    private BigDecimal horasDisponibles;
    private BigDecimal pctOcupacion;
    private BigDecimal ingresosDia;
}

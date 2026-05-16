package cl.gamecenter.reporte.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteOcupacionResponseDTO {

    private Long id;
    private Long estacionId;
    private LocalDate fecha;
    private BigDecimal horasOcupadas;
    private BigDecimal horasDisponibles;
    private BigDecimal pctOcupacion;
    private BigDecimal ingresosDia;
}

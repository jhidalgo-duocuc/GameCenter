package cl.gamecenter.reporte.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteOcupacionRequestDTO {

    @NotNull
    private Long estacionId;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private BigDecimal horasOcupadas;

    @NotNull
    private BigDecimal horasDisponibles;

    @NotNull
    private BigDecimal pctOcupacion;

    @NotNull
    private BigDecimal ingresosDia;
}

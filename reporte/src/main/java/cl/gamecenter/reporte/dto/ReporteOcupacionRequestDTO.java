package cl.gamecenter.reporte.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteOcupacionRequestDTO {

    @NotNull(message = "La estación es obligatoria")
    private Long estacionId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "Las horas ocupadas son obligatorias")
    @PositiveOrZero(message = "Las horas ocupadas no pueden ser negativas")
    private BigDecimal horasOcupadas;

    @NotNull(message = "Las horas disponibles son obligatorias")
    @PositiveOrZero(message = "Las horas disponibles no pueden ser negativas")
    private BigDecimal horasDisponibles;

    @NotNull(message = "El porcentaje de ocupación es obligatorio")
    @PositiveOrZero(message = "El porcentaje de ocupación no puede ser negativo")
    private BigDecimal pctOcupacion;

    @NotNull(message = "Los ingresos del día son obligatorios")
    @PositiveOrZero(message = "Los ingresos del día no pueden ser negativos")
    private BigDecimal ingresosDia;
}

package cl.gamecenter.reporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotIngresoRequestDTO {

    @NotBlank(message = "El periodo es obligatorio")
    private String periodo;

    @NotNull(message = "El total de sesiones es obligatorio")
    @PositiveOrZero(message = "El total de sesiones no puede ser negativo")
    private Integer totalSesiones;

    @NotNull(message = "El total de membresías es obligatorio")
    @PositiveOrZero(message = "El total de membresías no puede ser negativo")
    private Integer totalMembresias;

    @NotNull(message = "Los ingresos brutos son obligatorios")
    @PositiveOrZero(message = "Los ingresos brutos no pueden ser negativos")
    private BigDecimal ingresosBrutos;

    @NotNull(message = "El total de descuentos es obligatorio")
    @PositiveOrZero(message = "El total de descuentos no puede ser negativo")
    private BigDecimal descuentosTotal;

    @NotNull(message = "Los ingresos netos son obligatorios")
    @PositiveOrZero(message = "Los ingresos netos no pueden ser negativos")
    private BigDecimal ingresosNetos;

    @NotNull(message = "La fecha de generación es obligatoria")
    private LocalDateTime generatedAt;
}

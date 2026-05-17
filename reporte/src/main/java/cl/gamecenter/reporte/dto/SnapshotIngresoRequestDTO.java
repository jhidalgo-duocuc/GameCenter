package cl.gamecenter.reporte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotIngresoRequestDTO {

    @NotBlank
    private String periodo;

    @NotNull
    private Integer totalSesiones;

    @NotNull
    private Integer totalMembresias;

    @NotNull
    private BigDecimal ingresosBrutos;

    @NotNull
    private BigDecimal descuentosTotal;

    @NotNull
    private BigDecimal ingresosNetos;

    @NotNull
    private LocalDateTime generatedAt;
}

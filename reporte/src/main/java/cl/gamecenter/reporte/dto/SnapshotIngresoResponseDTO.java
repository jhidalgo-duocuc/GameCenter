package cl.gamecenter.reporte.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotIngresoResponseDTO {

    private Long id;
    private String periodo;
    private Integer totalSesiones;
    private Integer totalMembresias;
    private BigDecimal ingresosBrutos;
    private BigDecimal descuentosTotal;
    private BigDecimal ingresosNetos;
    private LocalDateTime generatedAt;
}

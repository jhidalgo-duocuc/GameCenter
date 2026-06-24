package cl.gamecenter.promocion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoDescuentoRequestDTO {

    @NotNull(message = "La promoción es obligatoria")
    private Long promocionId;

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotNull(message = "Los usos máximos son obligatorios")
    @PositiveOrZero(message = "Los usos máximos no pueden ser negativos")
    private Integer usosMax;

    @NotNull(message = "Los usos actuales son obligatorios")
    @PositiveOrZero(message = "Los usos actuales no pueden ser negativos")
    private Integer usosActuales;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}

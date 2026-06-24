package cl.gamecenter.lista_espera.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEsperaRequestDTO {

    @NotNull(message = "Los minutos para confirmar son obligatorios")
    @Positive(message = "Los minutos para confirmar deben ser mayor a 0")
    private Integer minutosParaConfirmar;

    @NotNull(message = "Los intentos máximos son obligatorios")
    @Positive(message = "Los intentos máximos deben ser mayor a 0")
    private Integer maxIntentos;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}

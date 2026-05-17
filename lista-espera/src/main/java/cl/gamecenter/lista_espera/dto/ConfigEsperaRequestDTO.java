package cl.gamecenter.lista_espera.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEsperaRequestDTO {

    @NotNull
    private Integer minutosParaConfirmar;

    @NotNull
    private Integer maxIntentos;

    @NotNull
    private Boolean activo;
}

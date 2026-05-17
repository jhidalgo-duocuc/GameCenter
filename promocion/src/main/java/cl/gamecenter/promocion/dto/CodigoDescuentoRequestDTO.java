package cl.gamecenter.promocion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoDescuentoRequestDTO {

    @NotNull
    private Long promocionId;

    @NotBlank
    private String codigo;

    @NotNull
    private Integer usosMax;

    @NotNull
    private Integer usosActuales;

    @NotNull
    private Boolean activo;
}

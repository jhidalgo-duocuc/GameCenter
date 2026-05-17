package cl.gamecenter.estacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstacionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String especificaciones;

    @NotNull(message = "El tipo de estacion es obligatorio")
    private Long tipoEstacionId;
}

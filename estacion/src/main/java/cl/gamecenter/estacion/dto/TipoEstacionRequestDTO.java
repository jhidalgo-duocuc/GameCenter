package cl.gamecenter.estacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TipoEstacionRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio por hora es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precioHora;
}

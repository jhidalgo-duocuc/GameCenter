package cl.gamecenter.membresia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoMembresiaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El precio mensual es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precioMensual;

    @NotNull(message = "Las horas incluidas son obligatorias")
    @Positive(message = "Las horas deben ser mayor a 0")
    private Integer horasIncluidas;

    @NotNull(message = "El descuento es obligatorio")
    @Positive(message = "El descuento debe ser mayor a 0")
    private BigDecimal descuentoPct;
}

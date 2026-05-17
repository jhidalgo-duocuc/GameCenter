package cl.gamecenter.control_tiempo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SesionRequestDTO {

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;

    @NotNull(message = "La estacion es obligatoria")
    private Long estacionId;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "La tarifa por hora es obligatoria")
    @Positive(message = "La tarifa debe ser mayor a 0")
    private BigDecimal tarifaPorHora;
}

package cl.gamecenter.control_tiempo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SesionResponseDTO {

    private Long id;
    private Long reservaId;
    private Long estacionId;
    private Long usuarioId;
    private LocalDateTime inicioReal;
    private LocalDateTime finReal;
    private Integer minutosConsumidos;
    private BigDecimal tarifaPorHora;
    private BigDecimal totalCalculado;
    private String estado;

}

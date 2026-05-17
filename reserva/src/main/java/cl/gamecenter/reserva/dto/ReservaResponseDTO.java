package cl.gamecenter.reserva.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long estacionId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String notas;
    private LocalDateTime createdAt;
}

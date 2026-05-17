package cl.gamecenter.membresia.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MembresiaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long tipoMembresiaId;
    private String tipoMembresiaNombre;
    private BigDecimal descuentoPct;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private LocalDateTime createdAt;
}

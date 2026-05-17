package cl.gamecenter.estacion.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EstacionResponseDTO {

    private Long id;
    private String nombre;
    private String especificaciones;
    private String estado;
    private Long tipoEstacionId;
    private String tipoEstacionNombre;
    private BigDecimal precioHora;
    private LocalDateTime updatedAt;
}

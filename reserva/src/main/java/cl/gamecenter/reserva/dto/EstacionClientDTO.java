package cl.gamecenter.reserva.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EstacionClientDTO {

    private Long id;
    private String nombre;
    private String estado;
    private Long tipoEstacionId;
    private String tipoEstacionNombre;
    private BigDecimal precioHora;
}

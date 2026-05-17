package cl.gamecenter.estacion.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoEstacionResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioHora;
    private Boolean activo;
}

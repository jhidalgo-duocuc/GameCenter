package cl.gamecenter.membresia.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoMembresiaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioMensual;
    private Integer horasIncluidas;
    private BigDecimal descuentoPct;
    private Boolean activo;
}

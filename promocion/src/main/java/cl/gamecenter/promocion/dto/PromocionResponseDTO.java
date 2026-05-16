package cl.gamecenter.promocion.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromocionResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private BigDecimal descuentoPct;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;
}

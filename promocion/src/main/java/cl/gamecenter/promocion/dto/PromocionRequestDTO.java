package cl.gamecenter.promocion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromocionRequestDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String tipo;

    @NotNull
    private BigDecimal descuentoPct;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaFin;

    @NotNull
    private Boolean activo;
}

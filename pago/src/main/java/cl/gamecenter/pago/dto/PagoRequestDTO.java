package cl.gamecenter.pago.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El tipo de pago es obligatorio")
    private String tipo;

    private Long sesionId;
    private Long membresiaId;
    private Long promocionId;

    @NotNull(message = "El monto bruto es obligatorio")
    @PositiveOrZero(message = "El monto bruto no puede ser negativo")
    private BigDecimal montoBruto;

    @NotNull(message = "El descuento aplicado es obligatorio")
    @PositiveOrZero(message = "El descuento aplicado no puede ser negativo")
    private BigDecimal descuentoAplicado;

    @NotNull(message = "El monto final es obligatorio")
    @PositiveOrZero(message = "El monto final no puede ser negativo")
    private BigDecimal montoFinal;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "La referencia externa es obligatoria")
    private String referenciaExterna;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDateTime fechaPago;
}

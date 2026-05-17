package cl.gamecenter.pago.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotBlank
    private String tipo;

    private Long sesionId;
    private Long membresiaId;
    private Long promocionId;

    @NotNull
    private BigDecimal montoBruto;

    @NotNull
    private BigDecimal descuentoAplicado;

    @NotNull
    private BigDecimal montoFinal;

    @NotBlank
    private String metodoPago;

    @NotBlank
    private String estado;

    @NotBlank
    private String referenciaExterna;

    @NotNull
    private LocalDateTime fechaPago;
}

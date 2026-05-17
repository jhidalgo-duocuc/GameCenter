package cl.gamecenter.pago.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private Long id;
    private Long usuarioId;
    private String tipo;
    private Long sesionId;
    private Long membresiaId;
    private Long promocionId;
    private BigDecimal montoBruto;
    private BigDecimal descuentoAplicado;
    private BigDecimal montoFinal;
    private String metodoPago;
    private String estado;
    private String referenciaExterna;
    private LocalDateTime fechaPago;
}

package cl.gamecenter.promocion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsoPromocionRequestDTO {

    @NotNull(message = "El código de descuento es obligatorio")
    private Long codigoDescuentoId;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El pago es obligatorio")
    private Long pagoId;

    @NotNull(message = "La fecha de uso es obligatoria")
    private LocalDateTime usadoEn;
}

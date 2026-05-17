package cl.gamecenter.promocion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsoPromocionRequestDTO {

    @NotNull
    private Long codigoDescuentoId;

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long pagoId;

    @NotNull
    private LocalDateTime usadoEn;
}

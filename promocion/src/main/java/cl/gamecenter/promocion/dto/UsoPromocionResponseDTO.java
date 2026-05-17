package cl.gamecenter.promocion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsoPromocionResponseDTO {

    private Long id;
    private Long codigoDescuentoId;
    private Long usuarioId;
    private Long pagoId;
    private LocalDateTime usadoEn;
}

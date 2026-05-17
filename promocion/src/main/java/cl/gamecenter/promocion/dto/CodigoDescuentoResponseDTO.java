package cl.gamecenter.promocion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoDescuentoResponseDTO {

    private Long id;
    private Long promocionId;
    private String codigo;
    private Integer usosMax;
    private Integer usosActuales;
    private Boolean activo;
}

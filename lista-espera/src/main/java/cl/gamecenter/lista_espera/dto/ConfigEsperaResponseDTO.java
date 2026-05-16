package cl.gamecenter.lista_espera.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEsperaResponseDTO {

    private Long id;
    private Integer minutosParaConfirmar;
    private Integer maxIntentos;
    private Boolean activo;
}

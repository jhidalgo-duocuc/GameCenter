package cl.gamecenter.lista_espera.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaEsperaResponseDTO {

    private Long id;
    private Long usuarioId;
    private Long tipoEstacionId;
    private Integer posicion;
    private String estado;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaNotificacion;
    private LocalDateTime expiraEn;
}

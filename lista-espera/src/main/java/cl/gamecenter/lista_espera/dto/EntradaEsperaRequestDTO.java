package cl.gamecenter.lista_espera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaEsperaRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotNull
    private Long tipoEstacionId;

    @NotNull
    private Integer posicion;

    @NotBlank
    private String estado;

    @NotNull
    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaNotificacion;
    private LocalDateTime expiraEn;
}

package cl.gamecenter.lista_espera.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaEsperaRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El tipo de estación es obligatorio")
    private Long tipoEstacionId;

    @NotNull(message = "La posición es obligatoria")
    @Positive(message = "La posición debe ser mayor a 0")
    private Integer posicion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDateTime fechaIngreso;

    private LocalDateTime fechaNotificacion;
    private LocalDateTime expiraEn;
}

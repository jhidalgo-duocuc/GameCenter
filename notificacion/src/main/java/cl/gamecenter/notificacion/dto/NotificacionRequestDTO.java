package cl.gamecenter.notificacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionRequestDTO {

    @NotNull
    private Long usuarioId;

    @NotBlank
    private String tipo;

    @NotBlank
    private String titulo;

    @NotBlank
    private String mensaje;

    @NotNull
    private Boolean leida;

    @NotBlank
    private String canal;

    @NotNull
    private LocalDateTime createdAt;
}

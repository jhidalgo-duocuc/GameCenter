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

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "El estado leído es obligatorio")
    private Boolean leida;

    @NotBlank(message = "El canal es obligatorio")
    private String canal;

    @NotNull(message = "La fecha de creación es obligatoria")
    private LocalDateTime createdAt;
}

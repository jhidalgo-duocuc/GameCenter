package cl.gamecenter.notificacion.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionResponseDTO {

    private Long id;
    private Long usuarioId;
    private String tipo;
    private String titulo;
    private String mensaje;
    private Boolean leida;
    private String canal;
    private LocalDateTime createdAt;
}

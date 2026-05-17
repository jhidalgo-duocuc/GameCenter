package cl.gamecenter.lista_espera.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacionClientDTO {

    private Long usuarioId;
    private String tipo;
    private String titulo;
    private String mensaje;
    private Boolean leida;
    private String canal;
    private LocalDateTime createdAt;
}

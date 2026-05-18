package cl.gamecenter.notificacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    private String titulo;
    private String mensaje;
    private Boolean leida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CanalNotificacion canal;

    private LocalDateTime createdAt;

    public enum TipoNotificacion {
        LISTA_ESPERA,
        PAGO,
        PROMOCION,
        SISTEMA
    }

    public enum CanalNotificacion {
        APP,
        EMAIL,
        PUSH
    }
}

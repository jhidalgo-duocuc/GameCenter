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

    private String tipo;

    private String titulo;
    private String mensaje;
    private Boolean leida;

    private String canal;

    private LocalDateTime createdAt;
}

package cl.gamecenter.control_tiempo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sesion")
public class SesionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservaId;
    private Long estacionId;
    private Long usuarioId;
    private LocalDateTime inicioReal;
    private LocalDateTime finReal;
    private Integer minutosConsumidos;
    private BigDecimal tarifaPorHora;
    private BigDecimal totalCalculado;
    @Enumerated(EnumType.STRING)
    private EstadoSesion estado;

    public enum EstadoSesion {
        activa,
        cerrada,
        cancelada
    }
}

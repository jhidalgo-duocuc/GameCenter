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

    @Column(nullable = false)
    private Long reservaId;

    @Column(nullable = false)
    private Long estacionId;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private LocalDateTime inicioReal;
    private LocalDateTime finReal;
    private Integer minutosConsumidos;

    @Column(nullable = false)
    private BigDecimal tarifaPorHora;
    private BigDecimal totalCalculado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSesion estado;


    @PrePersist
    protected void onCreate() {
        this.estado = EstadoSesion.ACTIVA;
        this.inicioReal = LocalDateTime.now();
    }

    public enum EstadoSesion {
        ACTIVA,
        CERRADA,
        CANCELADA
    }
}

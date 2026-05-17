package cl.gamecenter.membresia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "membresia")
public class MembresiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "tipo_membresia_id", nullable = false)
    private TipoMembresiaEntity tipoMembresia;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMembresia estado;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.estado = EstadoMembresia.ACTIVA;
        this.fechaInicio = LocalDate.now();
        this.fechaFin = LocalDate.now().plusMonths(1);
    }

    public enum EstadoMembresia {
        ACTIVA,
        VENCIDA,
        CANCELADA
    }
}

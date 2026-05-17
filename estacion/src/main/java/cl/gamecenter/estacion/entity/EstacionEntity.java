package cl.gamecenter.estacion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "estacion")
public class EstacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_estacion_id", nullable = false)
    private TipoEstacionEntity tipoEstacion;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String especificaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEstacion estado;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.estado = EstadoEstacion.DISPONIBLE;
        this.updatedAt = LocalDateTime.now();
    }

    public enum EstadoEstacion {
        DISPONIBLE,
        OCUPADA,
        MANTENIMIENTO,
        INACTIVA
    }
}

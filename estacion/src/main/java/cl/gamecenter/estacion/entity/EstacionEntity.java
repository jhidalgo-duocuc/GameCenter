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
    private TipoEstacionEntity tipoEstacion;

    private String nombre;

    private String especificaciones;

    @Enumerated(EnumType.STRING)
    private EstadoEstacion estado;

    private LocalDateTime updateAt;

    public enum EstadoEstacion {
        disponible,
        ocupada,
        mantenimiento,
        inactiva
    }
}

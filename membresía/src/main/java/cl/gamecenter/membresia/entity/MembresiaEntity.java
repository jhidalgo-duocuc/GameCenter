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
    private Long usuarioId;
    @ManyToOne
    private TipoMembresiaEntity tipoMembresia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @Enumerated(EnumType.STRING)
    private EstadoMembresia estado;
    private LocalDateTime createdAt;

    public enum EstadoMembresia {
        activa,
        vencida,
        cancelada
    }
}

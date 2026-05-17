package cl.gamecenter.reserva.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    private Long estacionId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;
    private String notas;
    private LocalDateTime createAt;

    public enum EstadoReserva {
        pendiente,
        confirmada,
        en_curso,
        completada,
        cancelada
    }
}

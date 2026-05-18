package cl.gamecenter.pago.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPago tipo;

    @Column(nullable = true)
    private Long sesionId;

    @Column(nullable = true)
    private Long membresiaId;

    @Column(nullable = true)
    private Long promocionId;

    private BigDecimal montoBruto;
    private BigDecimal descuentoAplicado;
    private BigDecimal montoFinal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado;

    private String referenciaExterna;
    private LocalDateTime fechaPago;

    public enum TipoPago {
        SESION,
        MEMBRESIA
    }

    public enum MetodoPago {
        EFECTIVO,
        TARJETA,
        TRANSFERENCIA,
        QR
    }

    public enum EstadoPago {
        PENDIENTE,
        COMPLETADO,
        RECHAZADO,
        ANULADO
    }
}

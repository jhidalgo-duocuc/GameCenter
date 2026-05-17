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

    private String tipo;

    @Column(nullable = true)
    private Long sesionId;

    @Column(nullable = true)
    private Long membresiaId;

    @Column(nullable = true)
    private Long promocionId;

    private BigDecimal montoBruto;
    private BigDecimal descuentoAplicado;
    private BigDecimal montoFinal;

    private String metodoPago;
    private String estado;

    private String referenciaExterna;
    private LocalDateTime fechaPago;
}

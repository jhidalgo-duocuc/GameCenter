package cl.gamecenter.promocion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promocion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromocionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPromocion tipo;

    private BigDecimal descuentoPct;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Boolean activo;

    public enum TipoPromocion {
        PORCENTAJE,
        MONTO_FIJO
    }
}

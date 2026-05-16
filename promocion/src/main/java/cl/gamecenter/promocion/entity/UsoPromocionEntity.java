package cl.gamecenter.promocion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uso_promocion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsoPromocionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigo_descuento_id")
    private CodigoDescuentoEntity codigo;

    private Long usuarioId;
    private Long pagoId;
    private LocalDateTime usadoEn;
}

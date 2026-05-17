package cl.gamecenter.promocion.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "codigo_descuento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoDescuentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    private PromocionEntity promocion;

    @Column(unique = true)
    private String codigo;

    private Integer usosMax;
    private Integer usosActuales;
    private Boolean activo;
}

package cl.gamecenter.membresia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tipo_membresia")
public class TipoMembresiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precioMensual;

    @Column(nullable = false)
    private Integer horasIncluidas;

    @Column(nullable = false)
    private BigDecimal descuentoPct;

    @Column(nullable = false)
    private Boolean activo;
}

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
    private String nombre;
    private String descripcion;
    private BigDecimal precioMensual;
    private Integer horasIncluidas;
    private BigDecimal descuentoPct;
    private Boolean activo;
}

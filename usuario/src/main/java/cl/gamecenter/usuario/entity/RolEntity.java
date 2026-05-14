package cl.gamecenter.usuario.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="rol")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;
}

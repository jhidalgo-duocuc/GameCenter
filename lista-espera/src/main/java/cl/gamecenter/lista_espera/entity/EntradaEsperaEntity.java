package cl.gamecenter.lista_espera.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrada_espera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntradaEsperaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private Long tipoEstacionId;
    private Integer posicion;

    private String estado;

    private LocalDateTime fechaIngreso;

    @Column(nullable = true)
    private LocalDateTime fechaNotificacion;

    @Column(nullable = true)
    private LocalDateTime expiraEn;
}

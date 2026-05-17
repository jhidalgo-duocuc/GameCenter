package cl.gamecenter.lista_espera.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "config_espera")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEsperaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minutosParaConfirmar;
    private Integer maxIntentos;
    private Boolean activo;
}

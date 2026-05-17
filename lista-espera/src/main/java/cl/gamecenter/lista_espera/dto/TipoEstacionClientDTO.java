package cl.gamecenter.lista_espera.dto;

import lombok.Data;

@Data
public class TipoEstacionClientDTO {

    private Long id;
    private String nombre;
    private Boolean activo;
}

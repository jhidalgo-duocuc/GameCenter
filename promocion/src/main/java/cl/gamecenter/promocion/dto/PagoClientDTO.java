package cl.gamecenter.promocion.dto;

import lombok.Data;

@Data
public class PagoClientDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
}

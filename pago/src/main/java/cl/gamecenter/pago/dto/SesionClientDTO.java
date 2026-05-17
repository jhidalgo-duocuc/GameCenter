package cl.gamecenter.pago.dto;

import lombok.Data;

@Data
public class SesionClientDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
}

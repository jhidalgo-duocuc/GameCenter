package cl.gamecenter.pago.dto;

import lombok.Data;

@Data
public class MembresiaClientDTO {

    private Long id;
    private Long usuarioId;
    private String estado;
}

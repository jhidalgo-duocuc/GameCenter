package cl.gamecenter.membresia.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MembresiaRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El tipo de membresía es obligatorio")
    private Long tipoMembresiaId;
}

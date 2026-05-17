package cl.gamecenter.usuario.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginResponseDTO {

    private String token;
    private String tipo;             // "Bearer"
    private LocalDateTime expiraEn;
    private String nombreUsuario;
    private String rolNombre;
}

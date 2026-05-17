package cl.gamecenter.usuario.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Boolean activo;
    private String rolNombre;        // solo el nombre, no el objeto completo
    private LocalDateTime createdAt;
}

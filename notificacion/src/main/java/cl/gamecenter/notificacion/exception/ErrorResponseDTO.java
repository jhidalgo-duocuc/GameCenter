package cl.gamecenter.notificacion.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private int status;
    private String mensaje;
    private Map<String, String> errores;
}

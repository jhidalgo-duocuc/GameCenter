package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.NotificacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notificacion")
public interface NotificacionClient {

    @PostMapping("/api/notificaciones")
    void crear(@RequestBody NotificacionClientDTO request);
}

package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.NotificacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notificacion-service", url = "http://localhost:8088")
public interface NotificacionClient {

    @PostMapping("/api/notificaciones")
    void crear(@RequestBody NotificacionClientDTO request);
}

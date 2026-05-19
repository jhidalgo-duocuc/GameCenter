package cl.gamecenter.pago.client;

import cl.gamecenter.pago.dto.SesionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "control-tiempo")
public interface SesionClient {

    @GetMapping("/api/v1/sesiones/{id}")
    SesionClientDTO buscarPorId(@PathVariable Long id);
}

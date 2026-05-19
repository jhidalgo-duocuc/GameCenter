package cl.gamecenter.reporte.client;

import cl.gamecenter.reporte.dto.EstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estacion")
public interface EstacionClient {

    @GetMapping("/api/v1/estaciones/{id}")
    EstacionClientDTO buscarPorId(@PathVariable Long id);
}

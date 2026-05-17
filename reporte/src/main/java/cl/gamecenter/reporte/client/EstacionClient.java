package cl.gamecenter.reporte.client;

import cl.gamecenter.reporte.dto.EstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estacion-service", url = "http://localhost:8082")
public interface EstacionClient {

    @GetMapping("/api/estaciones/{id}")
    EstacionClientDTO buscarPorId(@PathVariable Long id);
}

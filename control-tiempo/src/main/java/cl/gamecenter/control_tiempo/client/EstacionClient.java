package cl.gamecenter.control_tiempo.client;

import cl.gamecenter.control_tiempo.client.dto.EstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "estacion-service", url = "http://localhost:8082")
public interface EstacionClient {

    @PutMapping("/api/estaciones/{id}/estado")
    EstacionClientDTO cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado);
}
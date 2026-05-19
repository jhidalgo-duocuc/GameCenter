package cl.gamecenter.reserva.client;

import cl.gamecenter.reserva.dto.EstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estacion")
public interface EstacionClient {

    @GetMapping("/api/v1/estaciones/{id}")
    EstacionClientDTO buscarPorId(@PathVariable Long id);

    @PutMapping("/api/v1/estaciones/{id}/estado")
    EstacionClientDTO cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado);
}

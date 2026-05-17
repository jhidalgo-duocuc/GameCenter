package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.TipoEstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estacion-service", url = "http://localhost:8082")
public interface TipoEstacionClient {

    @GetMapping("/api/tipos-estacion/{id}")
    TipoEstacionClientDTO buscarPorId(@PathVariable Long id);
}

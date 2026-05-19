package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.TipoEstacionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "estacion")
public interface TipoEstacionClient {

    @GetMapping("/api/v1/tipos-estacion/{id}")
    TipoEstacionClientDTO buscarPorId(@PathVariable Long id);
}

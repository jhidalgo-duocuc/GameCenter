package cl.gamecenter.notificacion.client;

import cl.gamecenter.notificacion.dto.UsuarioClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-service", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioClientDTO buscarPorId(@PathVariable Long id);
}

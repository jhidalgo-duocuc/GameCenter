package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.UsuarioClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-service", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioClientDTO buscarPorId(@PathVariable Long id);
}

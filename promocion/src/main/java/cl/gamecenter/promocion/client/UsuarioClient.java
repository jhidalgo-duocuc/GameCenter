package cl.gamecenter.promocion.client;

import cl.gamecenter.promocion.dto.UsuarioClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioClientDTO buscarPorId(@PathVariable Long id);
}

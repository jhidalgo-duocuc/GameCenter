package cl.gamecenter.pago.client;

import cl.gamecenter.pago.dto.UsuarioClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioClientDTO buscarPorId(@PathVariable Long id);
}

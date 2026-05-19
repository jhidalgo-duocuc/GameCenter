package cl.gamecenter.lista_espera.client;

import cl.gamecenter.lista_espera.dto.UsuarioClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioClientDTO buscarPorId(@PathVariable Long id);
}

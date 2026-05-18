package cl.gamecenter.pago.client;

import cl.gamecenter.pago.dto.MembresiaClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "membresia")
public interface MembresiaClient {

    @GetMapping("/api/membresias/{id}")
    MembresiaClientDTO buscarPorId(@PathVariable Long id);
}

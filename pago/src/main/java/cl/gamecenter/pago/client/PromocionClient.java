package cl.gamecenter.pago.client;

import cl.gamecenter.pago.dto.PromocionClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "promocion-service", url = "http://localhost:8090")
public interface PromocionClient {

    @GetMapping("/api/promociones/{id}")
    PromocionClientDTO buscarPorId(@PathVariable Long id);
}

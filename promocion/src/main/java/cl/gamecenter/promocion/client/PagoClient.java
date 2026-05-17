package cl.gamecenter.promocion.client;

import cl.gamecenter.promocion.dto.PagoClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pago-service", url = "http://localhost:8086")
public interface PagoClient {

    @GetMapping("/api/pagos/{id}")
    PagoClientDTO buscarPorId(@PathVariable Long id);
}

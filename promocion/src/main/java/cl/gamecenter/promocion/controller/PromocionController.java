package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.PromocionRequestDTO;
import cl.gamecenter.promocion.dto.PromocionResponseDTO;
import cl.gamecenter.promocion.service.PromocionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crear(@Valid @RequestBody PromocionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(promocionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PromocionResponseDTO>> listar() {
        return ResponseEntity.ok(promocionService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PromocionRequestDTO request) {
        return ResponseEntity.ok(promocionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

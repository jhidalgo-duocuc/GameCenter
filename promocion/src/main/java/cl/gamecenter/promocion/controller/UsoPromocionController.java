package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.UsoPromocionRequestDTO;
import cl.gamecenter.promocion.dto.UsoPromocionResponseDTO;
import cl.gamecenter.promocion.service.UsoPromocionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usos-promocion")
public class UsoPromocionController {

    private final UsoPromocionService usoPromocionService;

    public UsoPromocionController(UsoPromocionService usoPromocionService) {
        this.usoPromocionService = usoPromocionService;
    }

    @PostMapping
    public ResponseEntity<UsoPromocionResponseDTO> crear(@Valid @RequestBody UsoPromocionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usoPromocionService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsoPromocionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usoPromocionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UsoPromocionResponseDTO>> listar() {
        return ResponseEntity.ok(usoPromocionService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsoPromocionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsoPromocionRequestDTO request) {
        return ResponseEntity.ok(usoPromocionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usoPromocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

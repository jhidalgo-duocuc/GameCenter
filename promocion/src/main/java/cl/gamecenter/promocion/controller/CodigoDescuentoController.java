package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.CodigoDescuentoRequestDTO;
import cl.gamecenter.promocion.dto.CodigoDescuentoResponseDTO;
import cl.gamecenter.promocion.service.CodigoDescuentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/codigos-descuento")
public class CodigoDescuentoController {

    private final CodigoDescuentoService codigoDescuentoService;

    public CodigoDescuentoController(CodigoDescuentoService codigoDescuentoService) {
        this.codigoDescuentoService = codigoDescuentoService;
    }

    @PostMapping
    public ResponseEntity<CodigoDescuentoResponseDTO> crear(@Valid @RequestBody CodigoDescuentoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(codigoDescuentoService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodigoDescuentoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(codigoDescuentoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CodigoDescuentoResponseDTO>> listar() {
        return ResponseEntity.ok(codigoDescuentoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CodigoDescuentoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CodigoDescuentoRequestDTO request) {
        return ResponseEntity.ok(codigoDescuentoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        codigoDescuentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

package cl.gamecenter.lista_espera.controller;

import cl.gamecenter.lista_espera.dto.EntradaEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.EntradaEsperaResponseDTO;
import cl.gamecenter.lista_espera.service.EntradaEsperaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entradas-espera")
public class EntradaEsperaController {

    private final EntradaEsperaService entradaEsperaService;

    public EntradaEsperaController(EntradaEsperaService entradaEsperaService) {
        this.entradaEsperaService = entradaEsperaService;
    }

    @PostMapping
    public ResponseEntity<EntradaEsperaResponseDTO> crear(@Valid @RequestBody EntradaEsperaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entradaEsperaService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntradaEsperaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(entradaEsperaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EntradaEsperaResponseDTO>> listar() {
        return ResponseEntity.ok(entradaEsperaService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntradaEsperaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EntradaEsperaRequestDTO request) {
        return ResponseEntity.ok(entradaEsperaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entradaEsperaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

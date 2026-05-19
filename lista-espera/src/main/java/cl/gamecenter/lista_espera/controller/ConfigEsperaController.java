package cl.gamecenter.lista_espera.controller;

import cl.gamecenter.lista_espera.dto.ConfigEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.ConfigEsperaResponseDTO;
import cl.gamecenter.lista_espera.service.ConfigEsperaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/config-espera")
public class ConfigEsperaController {

    private final ConfigEsperaService configEsperaService;

    public ConfigEsperaController(ConfigEsperaService configEsperaService) {
        this.configEsperaService = configEsperaService;
    }

    @PostMapping
    public ResponseEntity<ConfigEsperaResponseDTO> crear(@Valid @RequestBody ConfigEsperaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configEsperaService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConfigEsperaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(configEsperaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ConfigEsperaResponseDTO>> listar() {
        return ResponseEntity.ok(configEsperaService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigEsperaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConfigEsperaRequestDTO request) {
        return ResponseEntity.ok(configEsperaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        configEsperaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

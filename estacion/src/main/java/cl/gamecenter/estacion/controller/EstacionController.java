package cl.gamecenter.estacion.controller;

import cl.gamecenter.estacion.dto.EstacionRequestDTO;
import cl.gamecenter.estacion.dto.EstacionResponseDTO;
import cl.gamecenter.estacion.service.EstacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
@RequiredArgsConstructor
public class EstacionController {

    private final EstacionService estacionService;

    @PostMapping
    public ResponseEntity<EstacionResponseDTO> crear(@Valid @RequestBody EstacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<EstacionResponseDTO>> listar() {
        return ResponseEntity.ok(estacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estacionService.buscarPorId(id));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<EstacionResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(estacionService.listarDisponibles());
    }

    @GetMapping("/tipo/{tipoEstacionId}")
    public ResponseEntity<List<EstacionResponseDTO>> listarPorTipo(@PathVariable Long tipoEstacionId) {
        return ResponseEntity.ok(estacionService.listarPorTipo(tipoEstacionId));
    }

    @GetMapping("/tipo/{tipoEstacionId}/disponibles")
    public ResponseEntity<List<EstacionResponseDTO>> listarDisponiblesPorTipo(@PathVariable Long tipoEstacionId) {
        return ResponseEntity.ok(estacionService.listarDisponiblesPorTipo(tipoEstacionId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<EstacionResponseDTO> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(estacionService.cambiarEstado(id, nuevoEstado));
    }
}

package cl.gamecenter.reporte.controller;

import cl.gamecenter.reporte.dto.SnapshotIngresoRequestDTO;
import cl.gamecenter.reporte.dto.SnapshotIngresoResponseDTO;
import cl.gamecenter.reporte.service.SnapshotIngresoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/snapshots-ingreso")
public class SnapshotIngresoController {

    private final SnapshotIngresoService snapshotIngresoService;

    public SnapshotIngresoController(SnapshotIngresoService snapshotIngresoService) {
        this.snapshotIngresoService = snapshotIngresoService;
    }

    @PostMapping
    public ResponseEntity<SnapshotIngresoResponseDTO> crear(@Valid @RequestBody SnapshotIngresoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(snapshotIngresoService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnapshotIngresoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(snapshotIngresoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<SnapshotIngresoResponseDTO>> listar() {
        return ResponseEntity.ok(snapshotIngresoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnapshotIngresoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SnapshotIngresoRequestDTO request) {
        return ResponseEntity.ok(snapshotIngresoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        snapshotIngresoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

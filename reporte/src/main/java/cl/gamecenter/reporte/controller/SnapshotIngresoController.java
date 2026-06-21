package cl.gamecenter.reporte.controller;

import cl.gamecenter.reporte.dto.SnapshotIngresoRequestDTO;
import cl.gamecenter.reporte.dto.SnapshotIngresoResponseDTO;
import cl.gamecenter.reporte.service.SnapshotIngresoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/snapshots-ingreso")
@Tag(name = "Snapshots de Ingreso", description = "Gestión de resúmenes de ingresos por período")
public class SnapshotIngresoController {

    private final SnapshotIngresoService snapshotIngresoService;

    public SnapshotIngresoController(SnapshotIngresoService snapshotIngresoService) {
        this.snapshotIngresoService = snapshotIngresoService;
    }

    @Operation(summary = "Crear snapshot de ingreso", description = "Registra un resumen de ingresos para un período determinado")
    @ApiResponse(responseCode = "201", description = "Snapshot creado exitosamente",
            content = @Content(schema = @Schema(implementation = SnapshotIngresoResponseDTO.class)))
    @PostMapping
    public ResponseEntity<SnapshotIngresoResponseDTO> crear(@Valid @RequestBody SnapshotIngresoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(snapshotIngresoService.crear(request));
    }

    @Operation(summary = "Obtener snapshot por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Snapshot encontrado",
                    content = @Content(schema = @Schema(implementation = SnapshotIngresoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Snapshot no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SnapshotIngresoResponseDTO> obtener(
            @Parameter(description = "ID del snapshot", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(snapshotIngresoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar snapshots de ingreso")
    @ApiResponse(responseCode = "200", description = "Lista de snapshots",
            content = @Content(schema = @Schema(implementation = SnapshotIngresoResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<SnapshotIngresoResponseDTO>> listar() {
        return ResponseEntity.ok(snapshotIngresoService.listar());
    }

    @Operation(summary = "Actualizar snapshot de ingreso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Snapshot actualizado",
                    content = @Content(schema = @Schema(implementation = SnapshotIngresoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Snapshot no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SnapshotIngresoResponseDTO> actualizar(
            @Parameter(description = "ID del snapshot", example = "1") @PathVariable Long id,
            @Valid @RequestBody SnapshotIngresoRequestDTO request) {
        return ResponseEntity.ok(snapshotIngresoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar snapshot de ingreso")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Snapshot eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Snapshot no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del snapshot", example = "1") @PathVariable Long id) {
        snapshotIngresoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
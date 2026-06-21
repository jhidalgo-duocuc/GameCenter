package cl.gamecenter.reporte.controller;

import cl.gamecenter.reporte.dto.ReporteOcupacionRequestDTO;
import cl.gamecenter.reporte.dto.ReporteOcupacionResponseDTO;
import cl.gamecenter.reporte.service.ReporteOcupacionService;
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
@RequestMapping("/api/v1/reportes-ocupacion")
@Tag(name = "Reportes de Ocupación", description = "Gestión de reportes diarios de ocupación por estación")
public class ReporteOcupacionController {

    private final ReporteOcupacionService reporteOcupacionService;

    public ReporteOcupacionController(ReporteOcupacionService reporteOcupacionService) {
        this.reporteOcupacionService = reporteOcupacionService;
    }

    @Operation(summary = "Crear reporte de ocupación", description = "Registra un reporte diario de ocupación para una estación validando que exista")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente",
                    content = @Content(schema = @Schema(implementation = ReporteOcupacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estación no encontrada", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReporteOcupacionResponseDTO> crear(@Valid @RequestBody ReporteOcupacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteOcupacionService.crear(request));
    }

    @Operation(summary = "Obtener reporte por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                    content = @Content(schema = @Schema(implementation = ReporteOcupacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReporteOcupacionResponseDTO> obtener(
            @Parameter(description = "ID del reporte", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(reporteOcupacionService.obtenerPorId(id));
    }

    @Operation(summary = "Listar reportes de ocupación")
    @ApiResponse(responseCode = "200", description = "Lista de reportes",
            content = @Content(schema = @Schema(implementation = ReporteOcupacionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<ReporteOcupacionResponseDTO>> listar() {
        return ResponseEntity.ok(reporteOcupacionService.listar());
    }

    @Operation(summary = "Actualizar reporte de ocupación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte actualizado",
                    content = @Content(schema = @Schema(implementation = ReporteOcupacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReporteOcupacionResponseDTO> actualizar(
            @Parameter(description = "ID del reporte", example = "1") @PathVariable Long id,
            @Valid @RequestBody ReporteOcupacionRequestDTO request) {
        return ResponseEntity.ok(reporteOcupacionService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar reporte de ocupación")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reporte eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del reporte", example = "1") @PathVariable Long id) {
        reporteOcupacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
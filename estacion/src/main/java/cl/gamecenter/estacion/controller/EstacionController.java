package cl.gamecenter.estacion.controller;

import cl.gamecenter.estacion.dto.EstacionRequestDTO;
import cl.gamecenter.estacion.dto.EstacionResponseDTO;
import cl.gamecenter.estacion.service.EstacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estaciones")
@RequiredArgsConstructor
@Tag(name = "Estaciones", description = "Gestión de estaciones de juego")
public class EstacionController {

    private final EstacionService estacionService;

    @Operation(summary = "Crear estación", description = "Registra una nueva estación asociada a un tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estación creada exitosamente",
                    content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o tipo de estación no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<EstacionResponseDTO> crear(@Valid @RequestBody EstacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionService.crear(dto));
    }

    @Operation(summary = "Listar estaciones", description = "Retorna todas las estaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de estaciones",
            content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<EstacionResponseDTO>> listar() {
        return ResponseEntity.ok(estacionService.listar());
    }

    @Operation(summary = "Buscar estación por ID", description = "Retorna una estación específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estación encontrada",
                    content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estación no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstacionResponseDTO> buscarPorId(
            @Parameter(description = "ID de la estación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(estacionService.buscarPorId(id));
    }

    @Operation(summary = "Listar estaciones disponibles", description = "Retorna estaciones en estado DISPONIBLE")
    @ApiResponse(responseCode = "200", description = "Lista de estaciones disponibles",
            content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class)))
    @GetMapping("/disponibles")
    public ResponseEntity<List<EstacionResponseDTO>> listarDisponibles() {
        return ResponseEntity.ok(estacionService.listarDisponibles());
    }

    @Operation(summary = "Listar estaciones por tipo", description = "Retorna estaciones filtradas por tipo")
    @ApiResponse(responseCode = "200", description = "Lista de estaciones del tipo indicado",
            content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class)))
    @GetMapping("/tipo/{tipoEstacionId}")
    public ResponseEntity<List<EstacionResponseDTO>> listarPorTipo(
            @Parameter(description = "ID del tipo de estación", example = "1") @PathVariable Long tipoEstacionId) {
        return ResponseEntity.ok(estacionService.listarPorTipo(tipoEstacionId));
    }

    @Operation(summary = "Listar estaciones disponibles por tipo", description = "Retorna estaciones DISPONIBLE de un tipo específico")
    @ApiResponse(responseCode = "200", description = "Lista de estaciones disponibles del tipo",
            content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class)))
    @GetMapping("/tipo/{tipoEstacionId}/disponibles")
    public ResponseEntity<List<EstacionResponseDTO>> listarDisponiblesPorTipo(
            @Parameter(description = "ID del tipo de estación", example = "1") @PathVariable Long tipoEstacionId) {
        return ResponseEntity.ok(estacionService.listarDisponiblesPorTipo(tipoEstacionId));
    }

    @Operation(summary = "Cambiar estado de estación", description = "Actualiza el estado operativo de una estación")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(schema = @Schema(implementation = EstacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Estación no encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<EstacionResponseDTO> cambiarEstado(
            @Parameter(description = "ID de la estación", example = "1") @PathVariable Long id,
            @Parameter(description = "Nuevo estado", example = "OCUPADA") @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(estacionService.cambiarEstado(id, nuevoEstado));
    }
}

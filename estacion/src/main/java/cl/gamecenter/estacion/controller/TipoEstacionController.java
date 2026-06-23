package cl.gamecenter.estacion.controller;

import cl.gamecenter.estacion.dto.TipoEstacionRequestDTO;
import cl.gamecenter.estacion.dto.TipoEstacionResponseDTO;
import cl.gamecenter.estacion.service.TipoEstacionService;
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
@RequestMapping("/api/v1/tipos-estacion")
@RequiredArgsConstructor
@Tag(name = "Tipos de Estación", description = "Gestión de categorías de estaciones de juego")
public class TipoEstacionController {

    private final TipoEstacionService tipoEstacionService;

    @Operation(summary = "Crear tipo de estación", description = "Registra un nuevo tipo con precio por hora")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo creado exitosamente",
                    content = @Content(schema = @Schema(implementation = TipoEstacionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre duplicado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TipoEstacionResponseDTO> crear(@Valid @RequestBody TipoEstacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEstacionService.crear(dto));
    }

    @Operation(summary = "Listar tipos de estación", description = "Retorna todos los tipos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de tipos",
            content = @Content(schema = @Schema(implementation = TipoEstacionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<TipoEstacionResponseDTO>> listar() {
        return ResponseEntity.ok(tipoEstacionService.listar());
    }

    @Operation(summary = "Listar tipos activos", description = "Retorna solo los tipos de estación activos")
    @ApiResponse(responseCode = "200", description = "Lista de tipos activos",
            content = @Content(schema = @Schema(implementation = TipoEstacionResponseDTO.class)))
    @GetMapping("/activos")
    public ResponseEntity<List<TipoEstacionResponseDTO>> listarActivos() {
        return ResponseEntity.ok(tipoEstacionService.listarActivos());
    }

    @Operation(summary = "Buscar tipo por ID", description = "Retorna un tipo de estación por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo encontrado",
                    content = @Content(schema = @Schema(implementation = TipoEstacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoEstacionResponseDTO> buscarPorId(
            @Parameter(description = "ID del tipo de estación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(tipoEstacionService.buscarPorId(id));
    }

    @Operation(summary = "Desactivar tipo de estación", description = "Marca un tipo de estación como inactivo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo desactivado",
                    content = @Content(schema = @Schema(implementation = TipoEstacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado",
                    content = @Content)
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<TipoEstacionResponseDTO> desactivar(
            @Parameter(description = "ID del tipo de estación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(tipoEstacionService.desactivar(id));
    }
}

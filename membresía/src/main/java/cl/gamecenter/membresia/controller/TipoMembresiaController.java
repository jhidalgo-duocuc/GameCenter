package cl.gamecenter.membresia.controller;

import cl.gamecenter.membresia.dto.TipoMembresiaRequestDTO;
import cl.gamecenter.membresia.dto.TipoMembresiaResponseDTO;
import cl.gamecenter.membresia.service.TipoMembresiaService;
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
@RequestMapping("/api/v1/tipos-membresia")
@RequiredArgsConstructor
@Tag(name = "Tipos de Membresía", description = "Gestión de planes de membresía disponibles")
public class TipoMembresiaController {

    private final TipoMembresiaService tipoMembresiaService;

    @Operation(summary = "Crear plan de membresía", description = "Registra un nuevo plan con precio y beneficios")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plan creado exitosamente",
                    content = @Content(schema = @Schema(implementation = TipoMembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre duplicado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<TipoMembresiaResponseDTO> crear(@Valid @RequestBody TipoMembresiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMembresiaService.crear(dto));
    }

    @Operation(summary = "Listar planes de membresía", description = "Retorna todos los planes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de planes",
            content = @Content(schema = @Schema(implementation = TipoMembresiaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<TipoMembresiaResponseDTO>> listar() {
        return ResponseEntity.ok(tipoMembresiaService.listar());
    }

    @Operation(summary = "Listar planes activos", description = "Retorna solo los planes de membresía activos")
    @ApiResponse(responseCode = "200", description = "Lista de planes activos",
            content = @Content(schema = @Schema(implementation = TipoMembresiaResponseDTO.class)))
    @GetMapping("/activos")
    public ResponseEntity<List<TipoMembresiaResponseDTO>> listarActivos() {
        return ResponseEntity.ok(tipoMembresiaService.listarActivos());
    }

    @Operation(summary = "Buscar plan por ID", description = "Retorna un plan de membresía por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plan encontrado",
                    content = @Content(schema = @Schema(implementation = TipoMembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoMembresiaResponseDTO> buscarPorId(
            @Parameter(description = "ID del plan", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(tipoMembresiaService.buscarPorId(id));
    }

    @Operation(summary = "Desactivar plan de membresía", description = "Marca un plan como inactivo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Plan desactivado",
                    content = @Content(schema = @Schema(implementation = TipoMembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado",
                    content = @Content)
    })
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<TipoMembresiaResponseDTO> desactivar(
            @Parameter(description = "ID del plan", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(tipoMembresiaService.desactivar(id));
    }
}

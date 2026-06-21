package cl.gamecenter.control_tiempo.controller;

import cl.gamecenter.control_tiempo.dto.SesionRequestDTO;
import cl.gamecenter.control_tiempo.dto.SesionResponseDTO;
import cl.gamecenter.control_tiempo.service.SesionService;
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
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
@Tag(name = "Sesiones", description = "Gestión de sesiones de juego y control de tiempo")
public class SesionController {

    private final SesionService sesionService;

    @Operation(summary = "Iniciar sesión", description = "Inicia una nueva sesión de juego en una estación, marcándola como OCUPADA")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sesión iniciada exitosamente",
                    content = @Content(schema = @Schema(implementation = SesionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La estación ya tiene una sesión activa",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<SesionResponseDTO> iniciar(@Valid @RequestBody SesionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionService.iniciar(dto));
    }

    @Operation(summary = "Cerrar sesión", description = "Cierra una sesión activa, calcula el tiempo consumido y el costo total")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión cerrada con costo calculado",
                    content = @Content(schema = @Schema(implementation = SesionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La sesión no está activa",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Sesión no encontrada",
                    content = @Content)
    })
    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<SesionResponseDTO> cerrar(
            @Parameter(description = "ID de la sesión", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(sesionService.cerrar(id));
    }

    @Operation(summary = "Listar sesiones", description = "Retorna todas las sesiones registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de sesiones",
            content = @Content(schema = @Schema(implementation = SesionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<SesionResponseDTO>> listar() {
        return ResponseEntity.ok(sesionService.listar());
    }

    @Operation(summary = "Buscar sesión por ID", description = "Retorna una sesión específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sesión encontrada",
                    content = @Content(schema = @Schema(implementation = SesionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sesión no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SesionResponseDTO> buscarPorId(
            @Parameter(description = "ID de la sesión", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(sesionService.buscarPorId(id));
    }

    @Operation(summary = "Listar sesiones activas", description = "Retorna todas las sesiones en estado ACTIVA")
    @ApiResponse(responseCode = "200", description = "Lista de sesiones activas",
            content = @Content(schema = @Schema(implementation = SesionResponseDTO.class)))
    @GetMapping("/activas")
    public ResponseEntity<List<SesionResponseDTO>> listarActivas() {
        return ResponseEntity.ok(sesionService.listarActivas());
    }

    @Operation(summary = "Listar sesiones por usuario", description = "Retorna todas las sesiones de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Lista de sesiones del usuario",
            content = @Content(schema = @Schema(implementation = SesionResponseDTO.class)))
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SesionResponseDTO>> listarPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(sesionService.listarPorUsuario(usuarioId));
    }
}
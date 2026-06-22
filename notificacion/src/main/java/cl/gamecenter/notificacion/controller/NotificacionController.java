package cl.gamecenter.notificacion.controller;

import cl.gamecenter.notificacion.dto.NotificacionRequestDTO;
import cl.gamecenter.notificacion.dto.NotificacionResponseDTO;
import cl.gamecenter.notificacion.service.NotificacionService;
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
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones", description = "Gestión de notificaciones enviadas a usuarios")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Crear notificación", description = "Envía una nueva notificación a un usuario activo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente",
                    content = @Content(schema = @Schema(implementation = NotificacionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario inactivo",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(request));
    }

    @Operation(summary = "Obtener notificación por ID", description = "Retorna una notificación específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación encontrada",
                    content = @Content(schema = @Schema(implementation = NotificacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> obtener(
            @Parameter(description = "ID de la notificación", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    @Operation(summary = "Listar notificaciones", description = "Retorna todas las notificaciones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones",
            content = @Content(schema = @Schema(implementation = NotificacionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @Operation(summary = "Actualizar notificación", description = "Actualiza los datos de una notificación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = NotificacionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizar(
            @Parameter(description = "ID de la notificación", example = "1") @PathVariable Long id,
            @Valid @RequestBody NotificacionRequestDTO request) {
        return ResponseEntity.ok(notificacionService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación del sistema por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notificación eliminada exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la notificación", example = "1") @PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

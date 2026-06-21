package cl.gamecenter.reserva.controller;

import cl.gamecenter.reserva.dto.ReservaRequestDTO;
import cl.gamecenter.reserva.dto.ReservaResponseDTO;
import cl.gamecenter.reserva.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Gestión de reservas de estaciones de juego")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Crear reserva", description = "Crea una nueva reserva para una estación en un rango de fechas")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o estación no disponible",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(dto));
    }

    @Operation(summary = "Listar reservas", description = "Retorna todas las reservas registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de reservas",
            content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @Operation(summary = "Buscar reserva por ID", description = "Retorna una reserva específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(
            @Parameter(description = "ID de la reserva", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    @Operation(summary = "Listar reservas por usuario", description = "Retorna todas las reservas de un usuario específico")
    @ApiResponse(responseCode = "200", description = "Lista de reservas del usuario",
            content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class)))
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaResponseDTO>> listarPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarPorUsuario(usuarioId));
    }

    @Operation(summary = "Confirmar reserva", description = "Cambia el estado de una reserva de PENDIENTE a CONFIRMADA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva confirmada",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content)
    })
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponseDTO> confirmar(
            @Parameter(description = "ID de la reserva", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.confirmar(id));
    }

    @Operation(summary = "Cancelar reserva", description = "Cancela una reserva que no esté en estado COMPLETADA o CANCELADA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva cancelada",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "No se puede cancelar en el estado actual",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content)
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(
            @Parameter(description = "ID de la reserva", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}
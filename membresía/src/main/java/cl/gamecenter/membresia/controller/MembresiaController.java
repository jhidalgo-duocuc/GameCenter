package cl.gamecenter.membresia.controller;

import cl.gamecenter.membresia.dto.MembresiaRequestDTO;
import cl.gamecenter.membresia.dto.MembresiaResponseDTO;
import cl.gamecenter.membresia.service.MembresiaService;
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
@RequestMapping("/api/v1/membresias")
@RequiredArgsConstructor
@Tag(name = "Membresías", description = "Gestión de membresías contratadas por usuarios")
public class MembresiaController {

    private final MembresiaService membresiaService;

    @Operation(summary = "Contratar membresía", description = "Contrata un plan de membresía para un usuario sin membresía activa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Membresía contratada exitosamente",
                    content = @Content(schema = @Schema(implementation = MembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Usuario con membresía activa o plan no disponible",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<MembresiaResponseDTO> contratar(@Valid @RequestBody MembresiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membresiaService.contratar(dto));
    }

    @Operation(summary = "Buscar membresía por ID", description = "Retorna una membresía específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membresía encontrada",
                    content = @Content(schema = @Schema(implementation = MembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MembresiaResponseDTO> buscarPorId(
            @Parameter(description = "ID de la membresía", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(membresiaService.buscarPorId(id));
    }

    @Operation(summary = "Listar membresías por usuario", description = "Retorna el historial de membresías de un usuario")
    @ApiResponse(responseCode = "200", description = "Lista de membresías del usuario",
            content = @Content(schema = @Schema(implementation = MembresiaResponseDTO.class)))
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MembresiaResponseDTO>> listarPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(membresiaService.listarPorUsuario(usuarioId));
    }

    @Operation(summary = "Buscar membresía activa por usuario", description = "Retorna la membresía activa de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membresía activa encontrada",
                    content = @Content(schema = @Schema(implementation = MembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no tiene membresía activa",
                    content = @Content)
    })
    @GetMapping("/usuario/{usuarioId}/activa")
    public ResponseEntity<MembresiaResponseDTO> buscarActivaPorUsuario(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(membresiaService.buscarActivaPorUsuario(usuarioId));
    }

    @Operation(summary = "Cancelar membresía", description = "Cancela una membresía que esté en estado ACTIVA")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Membresía cancelada",
                    content = @Content(schema = @Schema(implementation = MembresiaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Solo se puede cancelar una membresía activa",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Membresía no encontrada",
                    content = @Content)
    })
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<MembresiaResponseDTO> cancelar(
            @Parameter(description = "ID de la membresía", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(membresiaService.cancelar(id));
    }
}

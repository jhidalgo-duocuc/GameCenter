package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.PromocionRequestDTO;
import cl.gamecenter.promocion.dto.PromocionResponseDTO;
import cl.gamecenter.promocion.service.PromocionService;
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
@RequestMapping("/api/v1/promociones")
@Tag(name = "Promociones", description = "Gestión de promociones y descuentos")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @Operation(summary = "Crear promoción", description = "Registra una nueva promoción con fechas de vigencia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Promoción creada exitosamente",
                    content = @Content(schema = @Schema(implementation = PromocionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<PromocionResponseDTO> crear(@Valid @RequestBody PromocionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.crear(request));
    }

    @Operation(summary = "Obtener promoción por ID", description = "Retorna una promoción específica por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promoción encontrada",
                    content = @Content(schema = @Schema(implementation = PromocionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> obtener(
            @Parameter(description = "ID de la promoción", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(promocionService.obtenerPorId(id));
    }

    @Operation(summary = "Listar promociones", description = "Retorna todas las promociones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de promociones",
            content = @Content(schema = @Schema(implementation = PromocionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<PromocionResponseDTO>> listar() {
        return ResponseEntity.ok(promocionService.listar());
    }

    @Operation(summary = "Actualizar promoción", description = "Actualiza los datos de una promoción existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promoción actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = PromocionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponseDTO> actualizar(
            @Parameter(description = "ID de la promoción", example = "1") @PathVariable Long id,
            @Valid @RequestBody PromocionRequestDTO request) {
        return ResponseEntity.ok(promocionService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar promoción", description = "Elimina una promoción del sistema por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Promoción eliminada exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Promoción no encontrada",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la promoción", example = "1") @PathVariable Long id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

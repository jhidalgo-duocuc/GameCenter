package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.UsoPromocionRequestDTO;
import cl.gamecenter.promocion.dto.UsoPromocionResponseDTO;
import cl.gamecenter.promocion.service.UsoPromocionService;
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
@RequestMapping("/api/v1/usos-promocion")
@Tag(name = "Usos de Promoción", description = "Registro de uso de códigos de descuento en pagos")
public class UsoPromocionController {

    private final UsoPromocionService usoPromocionService;

    public UsoPromocionController(UsoPromocionService usoPromocionService) {
        this.usoPromocionService = usoPromocionService;
    }

    @Operation(summary = "Registrar uso de promoción", description = "Registra el uso de un código de descuento validando usuario y pago")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Uso registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = UsoPromocionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Usuario inactivo o pago no pertenece al usuario",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UsoPromocionResponseDTO> crear(@Valid @RequestBody UsoPromocionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usoPromocionService.crear(request));
    }

    @Operation(summary = "Obtener uso por ID", description = "Retorna un registro de uso de promoción por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Uso encontrado",
                    content = @Content(schema = @Schema(implementation = UsoPromocionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Uso no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsoPromocionResponseDTO> obtener(
            @Parameter(description = "ID del uso", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(usoPromocionService.obtenerPorId(id));
    }

    @Operation(summary = "Listar usos de promoción", description = "Retorna todos los usos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usos",
            content = @Content(schema = @Schema(implementation = UsoPromocionResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<UsoPromocionResponseDTO>> listar() {
        return ResponseEntity.ok(usoPromocionService.listar());
    }

    @Operation(summary = "Actualizar uso de promoción", description = "Actualiza un registro de uso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Uso actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = UsoPromocionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Uso no encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsoPromocionResponseDTO> actualizar(
            @Parameter(description = "ID del uso", example = "1") @PathVariable Long id,
            @Valid @RequestBody UsoPromocionRequestDTO request) {
        return ResponseEntity.ok(usoPromocionService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar uso de promoción", description = "Elimina un registro de uso por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Uso eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Uso no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del uso", example = "1") @PathVariable Long id) {
        usoPromocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

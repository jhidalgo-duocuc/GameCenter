package cl.gamecenter.promocion.controller;

import cl.gamecenter.promocion.dto.CodigoDescuentoRequestDTO;
import cl.gamecenter.promocion.dto.CodigoDescuentoResponseDTO;
import cl.gamecenter.promocion.service.CodigoDescuentoService;
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
@RequestMapping("/api/v1/codigos-descuento")
@Tag(name = "Códigos de Descuento", description = "Gestión de códigos promocionales")
public class CodigoDescuentoController {

    private final CodigoDescuentoService codigoDescuentoService;

    public CodigoDescuentoController(CodigoDescuentoService codigoDescuentoService) {
        this.codigoDescuentoService = codigoDescuentoService;
    }

    @Operation(summary = "Crear código de descuento", description = "Registra un nuevo código asociado a una promoción")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Código creado exitosamente",
                    content = @Content(schema = @Schema(implementation = CodigoDescuentoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<CodigoDescuentoResponseDTO> crear(@Valid @RequestBody CodigoDescuentoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(codigoDescuentoService.crear(request));
    }

    @Operation(summary = "Obtener código por ID", description = "Retorna un código de descuento por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código encontrado",
                    content = @Content(schema = @Schema(implementation = CodigoDescuentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Código no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CodigoDescuentoResponseDTO> obtener(
            @Parameter(description = "ID del código", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(codigoDescuentoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar códigos de descuento", description = "Retorna todos los códigos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de códigos",
            content = @Content(schema = @Schema(implementation = CodigoDescuentoResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<CodigoDescuentoResponseDTO>> listar() {
        return ResponseEntity.ok(codigoDescuentoService.listar());
    }

    @Operation(summary = "Actualizar código de descuento", description = "Actualiza los datos de un código existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = CodigoDescuentoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Código no encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CodigoDescuentoResponseDTO> actualizar(
            @Parameter(description = "ID del código", example = "1") @PathVariable Long id,
            @Valid @RequestBody CodigoDescuentoRequestDTO request) {
        return ResponseEntity.ok(codigoDescuentoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar código de descuento", description = "Elimina un código del sistema por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Código eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Código no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del código", example = "1") @PathVariable Long id) {
        codigoDescuentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

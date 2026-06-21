package cl.gamecenter.lista_espera.controller;

import cl.gamecenter.lista_espera.dto.EntradaEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.EntradaEsperaResponseDTO;
import cl.gamecenter.lista_espera.service.EntradaEsperaService;
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
@RequestMapping("/api/v1/entradas-espera")
@Tag(name = "Entradas de Espera", description = "Gestión de la cola de espera de usuarios por tipo de estación")
public class EntradaEsperaController {

    private final EntradaEsperaService entradaEsperaService;

    public EntradaEsperaController(EntradaEsperaService entradaEsperaService) {
        this.entradaEsperaService = entradaEsperaService;
    }

    @Operation(summary = "Crear entrada de espera", description = "Agrega un usuario a la cola de espera para un tipo de estación")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrada creada exitosamente",
                    content = @Content(schema = @Schema(implementation = EntradaEsperaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Usuario o tipo de estación inactivo",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntradaEsperaResponseDTO> crear(@Valid @RequestBody EntradaEsperaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entradaEsperaService.crear(request));
    }

    @Operation(summary = "Obtener entrada por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada encontrada",
                    content = @Content(schema = @Schema(implementation = EntradaEsperaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntradaEsperaResponseDTO> obtener(
            @Parameter(description = "ID de la entrada", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(entradaEsperaService.obtenerPorId(id));
    }

    @Operation(summary = "Listar entradas de espera")
    @ApiResponse(responseCode = "200", description = "Lista de entradas",
            content = @Content(schema = @Schema(implementation = EntradaEsperaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<EntradaEsperaResponseDTO>> listar() {
        return ResponseEntity.ok(entradaEsperaService.listar());
    }

    @Operation(summary = "Actualizar entrada de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada actualizada",
                    content = @Content(schema = @Schema(implementation = EntradaEsperaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntradaEsperaResponseDTO> actualizar(
            @Parameter(description = "ID de la entrada", example = "1") @PathVariable Long id,
            @Valid @RequestBody EntradaEsperaRequestDTO request) {
        return ResponseEntity.ok(entradaEsperaService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar entrada de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Entrada eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Entrada no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la entrada", example = "1") @PathVariable Long id) {
        entradaEsperaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
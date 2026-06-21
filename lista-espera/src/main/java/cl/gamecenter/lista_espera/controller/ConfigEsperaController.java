package cl.gamecenter.lista_espera.controller;

import cl.gamecenter.lista_espera.dto.ConfigEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.ConfigEsperaResponseDTO;
import cl.gamecenter.lista_espera.service.ConfigEsperaService;
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
@RequestMapping("/api/v1/config-espera")
@Tag(name = "Configuración de Espera", description = "Gestión de parámetros de la cola de espera")
public class ConfigEsperaController {

    private final ConfigEsperaService configEsperaService;

    public ConfigEsperaController(ConfigEsperaService configEsperaService) {
        this.configEsperaService = configEsperaService;
    }

    @Operation(summary = "Crear configuración de espera")
    @ApiResponse(responseCode = "201", description = "Configuración creada",
            content = @Content(schema = @Schema(implementation = ConfigEsperaResponseDTO.class)))
    @PostMapping
    public ResponseEntity<ConfigEsperaResponseDTO> crear(@Valid @RequestBody ConfigEsperaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(configEsperaService.crear(request));
    }

    @Operation(summary = "Obtener configuración por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Configuración encontrada",
                    content = @Content(schema = @Schema(implementation = ConfigEsperaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Configuración no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ConfigEsperaResponseDTO> obtener(
            @Parameter(description = "ID de la configuración", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(configEsperaService.obtenerPorId(id));
    }

    @Operation(summary = "Listar configuraciones de espera")
    @ApiResponse(responseCode = "200", description = "Lista de configuraciones",
            content = @Content(schema = @Schema(implementation = ConfigEsperaResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<ConfigEsperaResponseDTO>> listar() {
        return ResponseEntity.ok(configEsperaService.listar());
    }

    @Operation(summary = "Actualizar configuración de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Configuración actualizada",
                    content = @Content(schema = @Schema(implementation = ConfigEsperaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Configuración no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ConfigEsperaResponseDTO> actualizar(
            @Parameter(description = "ID de la configuración", example = "1") @PathVariable Long id,
            @Valid @RequestBody ConfigEsperaRequestDTO request) {
        return ResponseEntity.ok(configEsperaService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar configuración de espera")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Configuración eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Configuración no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la configuración", example = "1") @PathVariable Long id) {
        configEsperaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
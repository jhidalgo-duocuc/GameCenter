package cl.gamecenter.usuario.controller;

import cl.gamecenter.usuario.dto.RolRequestDTO;
import cl.gamecenter.usuario.dto.RolResponseDTO;
import cl.gamecenter.usuario.service.RolService;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Gestión de roles de usuario")
public class RolController {

    private final RolService rolService;

    @Operation(summary = "Crear rol", description = "Crea un nuevo rol en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente",
                    content = @Content(schema = @Schema(implementation = RolResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o rol duplicado",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(@Valid @RequestBody RolRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.crear(dto));
    }

    @Operation(summary = "Listar roles", description = "Retorna todos los roles registrados")
    @ApiResponse(responseCode = "200", description = "Lista de roles",
            content = @Content(schema = @Schema(implementation = RolResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listar() {
        return ResponseEntity.ok(rolService.listar());
    }

    @Operation(summary = "Buscar rol por ID", description = "Retorna un rol específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado",
                    content = @Content(schema = @Schema(implementation = RolResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> buscarPorId(
            @Parameter(description = "ID del rol", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }
}

package cl.gamecenter.pago.controller;

import cl.gamecenter.pago.dto.PagoRequestDTO;
import cl.gamecenter.pago.dto.PagoResponseDTO;
import cl.gamecenter.pago.service.PagoService;
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
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Gestión de pagos de sesiones y membresías")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(summary = "Registrar pago", description = "Crea un nuevo pago validando usuario activo, sesión/membresía y promoción vigente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Usuario inactivo, sesión/membresía inválida o promoción no vigente",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.crear(request));
    }

    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado",
                    content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> obtener(
            @Parameter(description = "ID del pago", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @Operation(summary = "Listar pagos", description = "Retorna todos los pagos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pagos",
            content = @Content(schema = @Schema(implementation = PagoResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listar() {
        return ResponseEntity.ok(pagoService.listar());
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = PagoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> actualizar(
            @Parameter(description = "ID del pago", example = "1") @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO request) {
        return ResponseEntity.ok(pagoService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un pago del sistema por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pago", example = "1") @PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
package cl.gamecenter.usuario.controller;

import cl.gamecenter.usuario.dto.LoginRequestDTO;
import cl.gamecenter.usuario.dto.LoginResponseDTO;
import cl.gamecenter.usuario.service.TokenAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Login y logout con token Bearer")
public class TokerAuthController {

    private final TokenAuthService tokenAuthService;

    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y genera un token de acceso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o usuario inactivo",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(tokenAuthService.login(dto));
    }

    @Operation(
            summary = "Cerrar sesión",
            description = "Invalida el token enviado en el header Authorization. Usa el botón Authorize arriba con el token del login.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logout exitoso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Token no encontrado",
                    content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @io.swagger.v3.oas.annotations.Parameter(
                    name = "Authorization",
                    in = ParameterIn.HEADER,
                    description = "Bearer + token del login",
                    example = "Bearer abc-123",
                    required = true
            )
            @RequestHeader("Authorization") String token) {
        String tokenValue = token.replace("Bearer ", "");
        tokenAuthService.invalidarToken(tokenValue);
        return ResponseEntity.noContent().build();
    }
}

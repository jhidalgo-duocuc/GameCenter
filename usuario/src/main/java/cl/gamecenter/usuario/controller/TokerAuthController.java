package cl.gamecenter.usuario.controller;

import cl.gamecenter.usuario.dto.LoginRequestDTO;
import cl.gamecenter.usuario.dto.LoginResponseDTO;
import cl.gamecenter.usuario.service.TokenAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class TokerAuthController {

    private final TokenAuthService tokenAuthService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(tokenAuthService.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // Quita el prefijo "Bearer "
        String tokenValue = token.replace("Bearer ", "");
        tokenAuthService.invalidarToken(tokenValue);
        return ResponseEntity.noContent().build();
    }
}

package cl.gamecenter.control_tiempo.controller;

import cl.gamecenter.control_tiempo.dto.SesionRequestDTO;
import cl.gamecenter.control_tiempo.dto.SesionResponseDTO;
import cl.gamecenter.control_tiempo.service.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;

    @PostMapping
    public ResponseEntity<SesionResponseDTO> iniciar(@Valid @RequestBody SesionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sesionService.iniciar(dto));
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<SesionResponseDTO> cerrar(@PathVariable Long id) {
        return ResponseEntity.ok(sesionService.cerrar(id));
    }

    @GetMapping
    public ResponseEntity<List<SesionResponseDTO>> listar() {
        return ResponseEntity.ok(sesionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sesionService.buscarPorId(id));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<SesionResponseDTO>> listarActivas() {
        return ResponseEntity.ok(sesionService.listarActivas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SesionResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(sesionService.listarPorUsuario(usuarioId));
    }
}

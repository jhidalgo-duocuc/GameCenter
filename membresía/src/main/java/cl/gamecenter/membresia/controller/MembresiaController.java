package cl.gamecenter.membresia.controller;

import cl.gamecenter.membresia.dto.MembresiaRequestDTO;
import cl.gamecenter.membresia.dto.MembresiaResponseDTO;
import cl.gamecenter.membresia.service.MembresiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membresias")
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    @PostMapping
    public ResponseEntity<MembresiaResponseDTO> contratar(@Valid @RequestBody MembresiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membresiaService.contratar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembresiaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(membresiaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MembresiaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(membresiaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/activa")
    public ResponseEntity<MembresiaResponseDTO> buscarActivaPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(membresiaService.buscarActivaPorUsuario(usuarioId));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<MembresiaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(membresiaService.cancelar(id));
    }
}

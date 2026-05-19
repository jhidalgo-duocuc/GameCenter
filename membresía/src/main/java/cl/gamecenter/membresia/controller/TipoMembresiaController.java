package cl.gamecenter.membresia.controller;

import cl.gamecenter.membresia.dto.TipoMembresiaRequestDTO;
import cl.gamecenter.membresia.dto.TipoMembresiaResponseDTO;
import cl.gamecenter.membresia.service.TipoMembresiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos-membresia")
@RequiredArgsConstructor
public class TipoMembresiaController {

    private final TipoMembresiaService tipoMembresiaService;

    @PostMapping
    public ResponseEntity<TipoMembresiaResponseDTO> crear(@Valid @RequestBody TipoMembresiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMembresiaService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoMembresiaResponseDTO>> listar() {
        return ResponseEntity.ok(tipoMembresiaService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TipoMembresiaResponseDTO>> listarActivos() {
        return ResponseEntity.ok(tipoMembresiaService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMembresiaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoMembresiaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<TipoMembresiaResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(tipoMembresiaService.desactivar(id));
    }
}

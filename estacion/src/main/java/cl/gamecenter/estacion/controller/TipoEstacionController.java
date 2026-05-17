package cl.gamecenter.estacion.controller;

import cl.gamecenter.estacion.dto.TipoEstacionRequestDTO;
import cl.gamecenter.estacion.dto.TipoEstacionResponseDTO;
import cl.gamecenter.estacion.service.TipoEstacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-estacion")
@RequiredArgsConstructor
public class TipoEstacionController {

    private final TipoEstacionService tipoEstacionService;

    @PostMapping
    public ResponseEntity<TipoEstacionResponseDTO> crear(@Valid @RequestBody TipoEstacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEstacionService.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<TipoEstacionResponseDTO>> listar() {
        return ResponseEntity.ok(tipoEstacionService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<TipoEstacionResponseDTO>> listarActivos() {
        return ResponseEntity.ok(tipoEstacionService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEstacionResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tipoEstacionService.buscarPorId(id));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<TipoEstacionResponseDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(tipoEstacionService.desactivar(id));
    }
}

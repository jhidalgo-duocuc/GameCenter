package cl.gamecenter.reporte.controller;

import cl.gamecenter.reporte.dto.ReporteOcupacionRequestDTO;
import cl.gamecenter.reporte.dto.ReporteOcupacionResponseDTO;
import cl.gamecenter.reporte.service.ReporteOcupacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes-ocupacion")
public class ReporteOcupacionController {

    private final ReporteOcupacionService reporteOcupacionService;

    public ReporteOcupacionController(ReporteOcupacionService reporteOcupacionService) {
        this.reporteOcupacionService = reporteOcupacionService;
    }

    @PostMapping
    public ResponseEntity<ReporteOcupacionResponseDTO> crear(@Valid @RequestBody ReporteOcupacionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteOcupacionService.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteOcupacionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(reporteOcupacionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ReporteOcupacionResponseDTO>> listar() {
        return ResponseEntity.ok(reporteOcupacionService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteOcupacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ReporteOcupacionRequestDTO request) {
        return ResponseEntity.ok(reporteOcupacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteOcupacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

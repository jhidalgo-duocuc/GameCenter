package cl.gamecenter.reporte.service;

import cl.gamecenter.reporte.dto.SnapshotIngresoRequestDTO;
import cl.gamecenter.reporte.dto.SnapshotIngresoResponseDTO;
import cl.gamecenter.reporte.entity.SnapshotIngresoEntity;
import cl.gamecenter.reporte.repository.SnapshotIngresoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SnapshotIngresoService {

    private final SnapshotIngresoRepository snapshotIngresoRepository;

    public SnapshotIngresoService(SnapshotIngresoRepository snapshotIngresoRepository) {
        this.snapshotIngresoRepository = snapshotIngresoRepository;
    }

    public SnapshotIngresoResponseDTO crear(SnapshotIngresoRequestDTO request) {
        SnapshotIngresoEntity guardado = snapshotIngresoRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public SnapshotIngresoResponseDTO obtenerPorId(Long id) {
        SnapshotIngresoEntity entity = snapshotIngresoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Snapshot de ingreso no encontrado"));
        return toResponse(entity);
    }

    public List<SnapshotIngresoResponseDTO> listar() {
        return snapshotIngresoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SnapshotIngresoResponseDTO actualizar(Long id, SnapshotIngresoRequestDTO request) {
        SnapshotIngresoEntity entity = snapshotIngresoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Snapshot de ingreso no encontrado"));
        aplicarRequest(entity, request);
        return toResponse(snapshotIngresoRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!snapshotIngresoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Snapshot de ingreso no encontrado");
        }
        snapshotIngresoRepository.deleteById(id);
    }

    private SnapshotIngresoEntity toEntity(SnapshotIngresoRequestDTO request) {
        SnapshotIngresoEntity entity = new SnapshotIngresoEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(SnapshotIngresoEntity entity, SnapshotIngresoRequestDTO request) {
        entity.setPeriodo(request.getPeriodo());
        entity.setTotalSesiones(request.getTotalSesiones());
        entity.setTotalMembresias(request.getTotalMembresias());
        entity.setIngresosBrutos(request.getIngresosBrutos());
        entity.setDescuentosTotal(request.getDescuentosTotal());
        entity.setIngresosNetos(request.getIngresosNetos());
        entity.setGeneratedAt(request.getGeneratedAt());
    }

    private SnapshotIngresoResponseDTO toResponse(SnapshotIngresoEntity entity) {
        SnapshotIngresoResponseDTO response = new SnapshotIngresoResponseDTO();
        response.setId(entity.getId());
        response.setPeriodo(entity.getPeriodo());
        response.setTotalSesiones(entity.getTotalSesiones());
        response.setTotalMembresias(entity.getTotalMembresias());
        response.setIngresosBrutos(entity.getIngresosBrutos());
        response.setDescuentosTotal(entity.getDescuentosTotal());
        response.setIngresosNetos(entity.getIngresosNetos());
        response.setGeneratedAt(entity.getGeneratedAt());
        return response;
    }
}

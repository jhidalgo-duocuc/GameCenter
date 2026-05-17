package cl.gamecenter.reporte.service;

import cl.gamecenter.reporte.client.EstacionClient;
import cl.gamecenter.reporte.dto.ReporteOcupacionRequestDTO;
import cl.gamecenter.reporte.dto.ReporteOcupacionResponseDTO;
import cl.gamecenter.reporte.entity.ReporteOcupacionEntity;
import cl.gamecenter.reporte.repository.ReporteOcupacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteOcupacionService {

    private final ReporteOcupacionRepository reporteOcupacionRepository;
    private final EstacionClient estacionClient;

    public ReporteOcupacionResponseDTO crear(ReporteOcupacionRequestDTO request) {
        estacionClient.buscarPorId(request.getEstacionId());

        ReporteOcupacionEntity guardado = reporteOcupacionRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public ReporteOcupacionResponseDTO obtenerPorId(Long id) {
        ReporteOcupacionEntity entity = reporteOcupacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte de ocupacion no encontrado"));
        return toResponse(entity);
    }

    public List<ReporteOcupacionResponseDTO> listar() {
        return reporteOcupacionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ReporteOcupacionResponseDTO actualizar(Long id, ReporteOcupacionRequestDTO request) {
        ReporteOcupacionEntity entity = reporteOcupacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte de ocupacion no encontrado"));
        aplicarRequest(entity, request);
        return toResponse(reporteOcupacionRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!reporteOcupacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte de ocupacion no encontrado");
        }
        reporteOcupacionRepository.deleteById(id);
    }

    private ReporteOcupacionEntity toEntity(ReporteOcupacionRequestDTO request) {
        ReporteOcupacionEntity entity = new ReporteOcupacionEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(ReporteOcupacionEntity entity, ReporteOcupacionRequestDTO request) {
        entity.setEstacionId(request.getEstacionId());
        entity.setFecha(request.getFecha());
        entity.setHorasOcupadas(request.getHorasOcupadas());
        entity.setHorasDisponibles(request.getHorasDisponibles());
        entity.setPctOcupacion(request.getPctOcupacion());
        entity.setIngresosDia(request.getIngresosDia());
    }

    private ReporteOcupacionResponseDTO toResponse(ReporteOcupacionEntity entity) {
        ReporteOcupacionResponseDTO response = new ReporteOcupacionResponseDTO();
        response.setId(entity.getId());
        response.setEstacionId(entity.getEstacionId());
        response.setFecha(entity.getFecha());
        response.setHorasOcupadas(entity.getHorasOcupadas());
        response.setHorasDisponibles(entity.getHorasDisponibles());
        response.setPctOcupacion(entity.getPctOcupacion());
        response.setIngresosDia(entity.getIngresosDia());
        return response;
    }
}

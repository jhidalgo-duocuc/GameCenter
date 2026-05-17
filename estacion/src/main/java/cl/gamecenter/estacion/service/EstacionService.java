package cl.gamecenter.estacion.service;

import cl.gamecenter.estacion.dto.EstacionRequestDTO;
import cl.gamecenter.estacion.dto.EstacionResponseDTO;
import cl.gamecenter.estacion.entity.EstacionEntity;
import cl.gamecenter.estacion.entity.TipoEstacionEntity;
import cl.gamecenter.estacion.repository.EstacionRepository;
import cl.gamecenter.estacion.repository.TipoEstacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstacionService {

    private final EstacionRepository estacionRepository;
    private final TipoEstacionRepository tipoEstacionRepository;

    public EstacionResponseDTO crear(EstacionRequestDTO dto) {
        TipoEstacionEntity tipo = tipoEstacionRepository.findById(dto.getTipoEstacionId())
                .orElseThrow(() -> new RuntimeException("Tipo de estacion no encontrado"));

        EstacionEntity entity = new EstacionEntity();
        entity.setNombre(dto.getNombre());
        entity.setEspecificaciones(dto.getEspecificaciones());
        entity.setTipoEstacion(tipo);

        return toResponse(estacionRepository.save(entity));
    }

    public List<EstacionResponseDTO> listar() {
        return estacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EstacionResponseDTO buscarPorId(Long id) {
        return toResponse(estacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estacion no encontrada")));
    }

    public List<EstacionResponseDTO> listarDisponibles() {
        return estacionRepository.findByEstado(EstacionEntity.EstadoEstacion.DISPONIBLE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EstacionResponseDTO> listarPorTipo(Long tipoEstacionId) {
        return estacionRepository.findByTipoEstacionId(tipoEstacionId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EstacionResponseDTO> listarDisponiblesPorTipo(Long tipoEstacionId) {
        return estacionRepository.findByTipoEstacionIdAndEstado(tipoEstacionId, EstacionEntity.EstadoEstacion.DISPONIBLE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EstacionResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        EstacionEntity entity = estacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estacion no encontrada"));
        entity.setEstado(EstacionEntity.EstadoEstacion.valueOf(nuevoEstado));
        return toResponse(estacionRepository.save(entity));
    }

    private EstacionResponseDTO toResponse(EstacionEntity entity) {
        EstacionResponseDTO dto = new EstacionResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setEspecificaciones(entity.getEspecificaciones());
        dto.setEstado(entity.getEstado().name());
        dto.setTipoEstacionId(entity.getTipoEstacion().getId());
        dto.setTipoEstacionNombre(entity.getTipoEstacion().getNombre());
        dto.setPrecioHora(entity.getTipoEstacion().getPrecioHora());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}

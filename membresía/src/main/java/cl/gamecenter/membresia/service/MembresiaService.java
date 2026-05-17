package cl.gamecenter.membresia.service;

import cl.gamecenter.membresia.dto.MembresiaRequestDTO;
import cl.gamecenter.membresia.dto.MembresiaResponseDTO;
import cl.gamecenter.membresia.entity.MembresiaEntity;
import cl.gamecenter.membresia.entity.TipoMembresiaEntity;
import cl.gamecenter.membresia.repository.MembresiaRepository;
import cl.gamecenter.membresia.repository.TipoMembresiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final TipoMembresiaRepository tipoMembresiaRepository;

    public MembresiaResponseDTO contratar(MembresiaRequestDTO dto) {
        // Verificar que no tenga membresía activa
        if (membresiaRepository.existsByUsuarioIdAndEstado(dto.getUsuarioId(), MembresiaEntity.EstadoMembresia.ACTIVA)) {
            throw new RuntimeException("El usuario ya tiene una membresía activa");
        }

        TipoMembresiaEntity tipo = tipoMembresiaRepository.findById(dto.getTipoMembresiaId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        if (!tipo.getActivo()) {
            throw new RuntimeException("El plan no está disponible");
        }

        MembresiaEntity entity = new MembresiaEntity();
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setTipoMembresia(tipo);

        return toResponse(membresiaRepository.save(entity));
    }

    public List<MembresiaResponseDTO> listarPorUsuario(Long usuarioId) {
        return membresiaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public MembresiaResponseDTO buscarActivaPorUsuario(Long usuarioId) {
        return toResponse(membresiaRepository.findByUsuarioIdAndEstado(usuarioId, MembresiaEntity.EstadoMembresia.ACTIVA)
                .orElseThrow(() -> new RuntimeException("El usuario no tiene membresía activa")));
    }

    public MembresiaResponseDTO cancelar(Long id) {
        MembresiaEntity entity = membresiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        if (entity.getEstado() != MembresiaEntity.EstadoMembresia.ACTIVA) {
            throw new RuntimeException("Solo se puede cancelar una membresía activa");
        }

        entity.setEstado(MembresiaEntity.EstadoMembresia.CANCELADA);
        return toResponse(membresiaRepository.save(entity));
    }

    public MembresiaResponseDTO buscarPorId(Long id) {
        return toResponse(membresiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada")));
    }

    private MembresiaResponseDTO toResponse(MembresiaEntity entity) {
        MembresiaResponseDTO dto = new MembresiaResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setTipoMembresiaId(entity.getTipoMembresia().getId());
        dto.setTipoMembresiaNombre(entity.getTipoMembresia().getNombre());
        dto.setDescuentoPct(entity.getTipoMembresia().getDescuentoPct());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setEstado(entity.getEstado().name());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}

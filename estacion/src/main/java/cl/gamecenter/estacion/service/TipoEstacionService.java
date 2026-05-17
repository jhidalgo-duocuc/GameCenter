package cl.gamecenter.estacion.service;

import cl.gamecenter.estacion.dto.TipoEstacionRequestDTO;
import cl.gamecenter.estacion.dto.TipoEstacionResponseDTO;
import cl.gamecenter.estacion.entity.TipoEstacionEntity;
import cl.gamecenter.estacion.repository.TipoEstacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEstacionService {

    private final TipoEstacionRepository tipoEstacionRepository;

    public TipoEstacionResponseDTO crear(TipoEstacionRequestDTO dto) {
        if (tipoEstacionRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un tipo de estacion con ese nombre");
        }

        TipoEstacionEntity entity = new TipoEstacionEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecioHora(dto.getPrecioHora());
        entity.setActivo(true);

        return toResponse(tipoEstacionRepository.save(entity));
    }

    public List<TipoEstacionResponseDTO> listar() {
        return tipoEstacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TipoEstacionResponseDTO> listarActivos() {
        return tipoEstacionRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TipoEstacionResponseDTO buscarPorId(Long id) {
        return toResponse(tipoEstacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de estacion no encontrado")));
    }

    public TipoEstacionResponseDTO desactivar(Long id) {
        TipoEstacionEntity entity = tipoEstacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de estacion no encontrado"));
        entity.setActivo(false);
        return toResponse(tipoEstacionRepository.save(entity));
    }

    private TipoEstacionResponseDTO toResponse(TipoEstacionEntity entity) {
        TipoEstacionResponseDTO dto = new TipoEstacionResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecioHora(entity.getPrecioHora());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}

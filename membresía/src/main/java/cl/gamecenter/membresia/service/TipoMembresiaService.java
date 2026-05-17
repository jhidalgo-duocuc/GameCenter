package cl.gamecenter.membresia.service;

import cl.gamecenter.membresia.dto.TipoMembresiaRequestDTO;
import cl.gamecenter.membresia.dto.TipoMembresiaResponseDTO;
import cl.gamecenter.membresia.entity.TipoMembresiaEntity;
import cl.gamecenter.membresia.repository.TipoMembresiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoMembresiaService {

    private final TipoMembresiaRepository tipoMembresiaRepository;

    public TipoMembresiaResponseDTO crear(TipoMembresiaRequestDTO dto) {
        if (tipoMembresiaRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un plan con ese nombre");
        }

        TipoMembresiaEntity entity = new TipoMembresiaEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecioMensual(dto.getPrecioMensual());
        entity.setHorasIncluidas(dto.getHorasIncluidas());
        entity.setDescuentoPct(dto.getDescuentoPct());
        entity.setActivo(true);

        return toResponse(tipoMembresiaRepository.save(entity));
    }

    public List<TipoMembresiaResponseDTO> listar() {
        return tipoMembresiaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TipoMembresiaResponseDTO> listarActivos() {
        return tipoMembresiaRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TipoMembresiaResponseDTO buscarPorId(Long id) {
        return toResponse(tipoMembresiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado")));
    }

    public TipoMembresiaResponseDTO desactivar(Long id) {
        TipoMembresiaEntity entity = tipoMembresiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        entity.setActivo(false);
        return toResponse(tipoMembresiaRepository.save(entity));
    }

    private TipoMembresiaResponseDTO toResponse(TipoMembresiaEntity entity) {
        TipoMembresiaResponseDTO dto = new TipoMembresiaResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecioMensual(entity.getPrecioMensual());
        dto.setHorasIncluidas(entity.getHorasIncluidas());
        dto.setDescuentoPct(entity.getDescuentoPct());
        dto.setActivo(entity.getActivo());
        return dto;
    }
}
package cl.gamecenter.usuario.service;

import cl.gamecenter.usuario.dto.RolRequestDTO;
import cl.gamecenter.usuario.dto.RolResponseDTO;
import cl.gamecenter.usuario.entity.RolEntity;
import cl.gamecenter.usuario.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public RolResponseDTO crear(RolRequestDTO dto) {
        if (rolRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }

        RolEntity entity = new RolEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        RolEntity guardado = rolRepository.save(entity);
        return toResponse(guardado);
    }

    public List<RolResponseDTO> listar() {
        return rolRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RolResponseDTO buscarPorId(Long id) {
        RolEntity entity = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return toResponse(entity);
    }

    // Mapper manual entity → DTO
    private RolResponseDTO toResponse(RolEntity entity) {
        RolResponseDTO dto = new RolResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
}

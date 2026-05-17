package cl.gamecenter.promocion.service;

import cl.gamecenter.promocion.dto.PromocionRequestDTO;
import cl.gamecenter.promocion.dto.PromocionResponseDTO;
import cl.gamecenter.promocion.entity.PromocionEntity;
import cl.gamecenter.promocion.repository.PromocionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionService(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    public PromocionResponseDTO crear(PromocionRequestDTO request) {
        PromocionEntity guardado = promocionRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public PromocionResponseDTO obtenerPorId(Long id) {
        PromocionEntity entity = promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));
        return toResponse(entity);
    }

    public List<PromocionResponseDTO> listar() {
        return promocionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PromocionResponseDTO actualizar(Long id, PromocionRequestDTO request) {
        PromocionEntity entity = promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));
        aplicarRequest(entity, request);
        return toResponse(promocionRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada");
        }
        promocionRepository.deleteById(id);
    }

    PromocionEntity obtenerEntidadPorId(Long id) {
        return promocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Promocion no encontrada"));
    }

    private PromocionEntity toEntity(PromocionRequestDTO request) {
        PromocionEntity entity = new PromocionEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(PromocionEntity entity, PromocionRequestDTO request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setTipo(request.getTipo());
        entity.setDescuentoPct(request.getDescuentoPct());
        entity.setFechaInicio(request.getFechaInicio());
        entity.setFechaFin(request.getFechaFin());
        entity.setActivo(request.getActivo());
    }

    private PromocionResponseDTO toResponse(PromocionEntity entity) {
        PromocionResponseDTO response = new PromocionResponseDTO();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setDescripcion(entity.getDescripcion());
        response.setTipo(entity.getTipo());
        response.setDescuentoPct(entity.getDescuentoPct());
        response.setFechaInicio(entity.getFechaInicio());
        response.setFechaFin(entity.getFechaFin());
        response.setActivo(entity.getActivo());
        return response;
    }
}

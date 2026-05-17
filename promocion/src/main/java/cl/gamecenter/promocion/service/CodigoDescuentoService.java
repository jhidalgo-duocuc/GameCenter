package cl.gamecenter.promocion.service;

import cl.gamecenter.promocion.dto.CodigoDescuentoRequestDTO;
import cl.gamecenter.promocion.dto.CodigoDescuentoResponseDTO;
import cl.gamecenter.promocion.entity.CodigoDescuentoEntity;
import cl.gamecenter.promocion.entity.PromocionEntity;
import cl.gamecenter.promocion.repository.CodigoDescuentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CodigoDescuentoService {

    private final CodigoDescuentoRepository codigoDescuentoRepository;
    private final PromocionService promocionService;

    public CodigoDescuentoService(
            CodigoDescuentoRepository codigoDescuentoRepository,
            PromocionService promocionService) {
        this.codigoDescuentoRepository = codigoDescuentoRepository;
        this.promocionService = promocionService;
    }

    public CodigoDescuentoResponseDTO crear(CodigoDescuentoRequestDTO request) {
        CodigoDescuentoEntity guardado = codigoDescuentoRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public CodigoDescuentoResponseDTO obtenerPorId(Long id) {
        CodigoDescuentoEntity entity = codigoDescuentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Codigo de descuento no encontrado"));
        return toResponse(entity);
    }

    public List<CodigoDescuentoResponseDTO> listar() {
        return codigoDescuentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public CodigoDescuentoResponseDTO actualizar(Long id, CodigoDescuentoRequestDTO request) {
        CodigoDescuentoEntity entity = codigoDescuentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Codigo de descuento no encontrado"));
        aplicarRequest(entity, request);
        return toResponse(codigoDescuentoRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!codigoDescuentoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Codigo de descuento no encontrado");
        }
        codigoDescuentoRepository.deleteById(id);
    }

    CodigoDescuentoEntity obtenerEntidadPorId(Long id) {
        return codigoDescuentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Codigo de descuento no encontrado"));
    }

    private CodigoDescuentoEntity toEntity(CodigoDescuentoRequestDTO request) {
        CodigoDescuentoEntity entity = new CodigoDescuentoEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(CodigoDescuentoEntity entity, CodigoDescuentoRequestDTO request) {
        PromocionEntity promocion = promocionService.obtenerEntidadPorId(request.getPromocionId());
        entity.setPromocion(promocion);
        entity.setCodigo(request.getCodigo());
        entity.setUsosMax(request.getUsosMax());
        entity.setUsosActuales(request.getUsosActuales());
        entity.setActivo(request.getActivo());
    }

    private CodigoDescuentoResponseDTO toResponse(CodigoDescuentoEntity entity) {
        CodigoDescuentoResponseDTO response = new CodigoDescuentoResponseDTO();
        response.setId(entity.getId());
        response.setPromocionId(entity.getPromocion().getId());
        response.setCodigo(entity.getCodigo());
        response.setUsosMax(entity.getUsosMax());
        response.setUsosActuales(entity.getUsosActuales());
        response.setActivo(entity.getActivo());
        return response;
    }
}

package cl.gamecenter.promocion.service;

import cl.gamecenter.promocion.dto.UsoPromocionRequestDTO;
import cl.gamecenter.promocion.dto.UsoPromocionResponseDTO;
import cl.gamecenter.promocion.entity.CodigoDescuentoEntity;
import cl.gamecenter.promocion.entity.UsoPromocionEntity;
import cl.gamecenter.promocion.repository.UsoPromocionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsoPromocionService {

    private final UsoPromocionRepository usoPromocionRepository;
    private final CodigoDescuentoService codigoDescuentoService;

    public UsoPromocionService(
            UsoPromocionRepository usoPromocionRepository,
            CodigoDescuentoService codigoDescuentoService) {
        this.usoPromocionRepository = usoPromocionRepository;
        this.codigoDescuentoService = codigoDescuentoService;
    }

    public UsoPromocionResponseDTO crear(UsoPromocionRequestDTO request) {
        UsoPromocionEntity guardado = usoPromocionRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public UsoPromocionResponseDTO obtenerPorId(Long id) {
        UsoPromocionEntity entity = usoPromocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Uso de promocion no encontrado"));
        return toResponse(entity);
    }

    public List<UsoPromocionResponseDTO> listar() {
        return usoPromocionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UsoPromocionResponseDTO actualizar(Long id, UsoPromocionRequestDTO request) {
        UsoPromocionEntity entity = usoPromocionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Uso de promocion no encontrado"));
        aplicarRequest(entity, request);
        return toResponse(usoPromocionRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!usoPromocionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Uso de promocion no encontrado");
        }
        usoPromocionRepository.deleteById(id);
    }

    private UsoPromocionEntity toEntity(UsoPromocionRequestDTO request) {
        UsoPromocionEntity entity = new UsoPromocionEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(UsoPromocionEntity entity, UsoPromocionRequestDTO request) {
        CodigoDescuentoEntity codigo = codigoDescuentoService.obtenerEntidadPorId(request.getCodigoDescuentoId());
        entity.setCodigo(codigo);
        entity.setUsuarioId(request.getUsuarioId());
        entity.setPagoId(request.getPagoId());
        entity.setUsadoEn(request.getUsadoEn());
    }

    private UsoPromocionResponseDTO toResponse(UsoPromocionEntity entity) {
        UsoPromocionResponseDTO response = new UsoPromocionResponseDTO();
        response.setId(entity.getId());
        response.setCodigoDescuentoId(entity.getCodigo().getId());
        response.setUsuarioId(entity.getUsuarioId());
        response.setPagoId(entity.getPagoId());
        response.setUsadoEn(entity.getUsadoEn());
        return response;
    }
}

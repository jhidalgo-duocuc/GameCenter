package cl.gamecenter.pago.service;

import cl.gamecenter.pago.dto.PagoRequestDTO;
import cl.gamecenter.pago.dto.PagoResponseDTO;
import cl.gamecenter.pago.entity.PagoEntity;
import cl.gamecenter.pago.repository.PagoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    public PagoResponseDTO crear(PagoRequestDTO request) {
        PagoEntity guardado = pagoRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public PagoResponseDTO obtenerPorId(Long id) {
        PagoEntity entity = pagoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        return toResponse(entity);
    }

    public List<PagoResponseDTO> listar() {
        return pagoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public PagoResponseDTO actualizar(Long id, PagoRequestDTO request) {
        PagoEntity entity = pagoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        aplicarRequest(entity, request);
        return toResponse(pagoRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado");
        }
        pagoRepository.deleteById(id);
    }

    private PagoEntity toEntity(PagoRequestDTO request) {
        PagoEntity entity = new PagoEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(PagoEntity entity, PagoRequestDTO request) {
        entity.setUsuarioId(request.getUsuarioId());
        entity.setTipo(request.getTipo());
        entity.setSesionId(request.getSesionId());
        entity.setMembresiaId(request.getMembresiaId());
        entity.setPromocionId(request.getPromocionId());
        entity.setMontoBruto(request.getMontoBruto());
        entity.setDescuentoAplicado(request.getDescuentoAplicado());
        entity.setMontoFinal(request.getMontoFinal());
        entity.setMetodoPago(request.getMetodoPago());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExterna(request.getReferenciaExterna());
        entity.setFechaPago(request.getFechaPago());
    }

    private PagoResponseDTO toResponse(PagoEntity entity) {
        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(entity.getId());
        response.setUsuarioId(entity.getUsuarioId());
        response.setTipo(entity.getTipo());
        response.setSesionId(entity.getSesionId());
        response.setMembresiaId(entity.getMembresiaId());
        response.setPromocionId(entity.getPromocionId());
        response.setMontoBruto(entity.getMontoBruto());
        response.setDescuentoAplicado(entity.getDescuentoAplicado());
        response.setMontoFinal(entity.getMontoFinal());
        response.setMetodoPago(entity.getMetodoPago());
        response.setEstado(entity.getEstado());
        response.setReferenciaExterna(entity.getReferenciaExterna());
        response.setFechaPago(entity.getFechaPago());
        return response;
    }
}

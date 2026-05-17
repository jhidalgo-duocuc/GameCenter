package cl.gamecenter.pago.service;

import cl.gamecenter.pago.client.MembresiaClient;
import cl.gamecenter.pago.client.PromocionClient;
import cl.gamecenter.pago.client.SesionClient;
import cl.gamecenter.pago.client.UsuarioClient;
import cl.gamecenter.pago.dto.*;
import cl.gamecenter.pago.entity.PagoEntity;
import cl.gamecenter.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioClient usuarioClient;
    private final SesionClient sesionClient;
    private final MembresiaClient membresiaClient;
    private final PromocionClient promocionClient;

    public PagoResponseDTO crear(PagoRequestDTO request) {
        UsuarioClientDTO usuario = usuarioClient.buscarPorId(request.getUsuarioId());
        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new RuntimeException("El usuario no está activo");
        }

        if (request.getSesionId() != null) {
            SesionClientDTO sesion = sesionClient.buscarPorId(request.getSesionId());
            if (!request.getUsuarioId().equals(sesion.getUsuarioId())) {
                throw new RuntimeException("La sesión no pertenece al usuario");
            }
        }

        if (request.getMembresiaId() != null) {
            MembresiaClientDTO membresia = membresiaClient.buscarPorId(request.getMembresiaId());
            if (!request.getUsuarioId().equals(membresia.getUsuarioId())) {
                throw new RuntimeException("La membresía no pertenece al usuario");
            }
        }

        if (request.getPromocionId() != null) {
            PromocionClientDTO promocion = promocionClient.buscarPorId(request.getPromocionId());
            if (!Boolean.TRUE.equals(promocion.getActivo())) {
                throw new RuntimeException("La promoción no está activa");
            }
            LocalDate hoy = LocalDate.now();
            if (hoy.isBefore(promocion.getFechaInicio()) || hoy.isAfter(promocion.getFechaFin())) {
                throw new RuntimeException("La promoción no está vigente");
            }
        }

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

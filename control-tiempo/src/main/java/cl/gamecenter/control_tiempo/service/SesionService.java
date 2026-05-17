package cl.gamecenter.control_tiempo.service;

import cl.gamecenter.control_tiempo.client.EstacionClient;
import cl.gamecenter.control_tiempo.dto.SesionRequestDTO;
import cl.gamecenter.control_tiempo.dto.SesionResponseDTO;
import cl.gamecenter.control_tiempo.entity.SesionEntity;
import cl.gamecenter.control_tiempo.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SessionRepository sesionRepository;
    private final EstacionClient estacionClient;

    public SesionResponseDTO iniciar(SesionRequestDTO dto) {

        // Verificar que no hay sesion activa en esa estacion
        sesionRepository.findByEstacionIdAndEstado(dto.getEstacionId(), SesionEntity.EstadoSesion.ACTIVA)
                .ifPresent(s -> {
                    throw new RuntimeException("La estacion ya tiene una sesion activa");
                });

        // Cambiar estado de estacion a OCUPADA via Feign
        estacionClient.cambiarEstado(dto.getEstacionId(), "OCUPADA");

        // Crear sesion
        SesionEntity entity = new SesionEntity();
        entity.setReservaId(dto.getReservaId());
        entity.setEstacionId(dto.getEstacionId());
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setTarifaPorHora(dto.getTarifaPorHora());

        return toResponse(sesionRepository.save(entity));
    }

    public SesionResponseDTO cerrar(Long id) {

        SesionEntity entity = sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));

        if (entity.getEstado() != SesionEntity.EstadoSesion.ACTIVA) {
            throw new RuntimeException("La sesion no está activa");
        }

        // Calcular tiempo y costo
        LocalDateTime fin = LocalDateTime.now();
        long minutos = Duration.between(entity.getInicioReal(), fin).toMinutes();
        if (minutos < 1) minutos = 1; // mínimo 1 minuto

        BigDecimal horas = BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), 4, RoundingMode.HALF_UP);
        BigDecimal total = entity.getTarifaPorHora().multiply(horas).setScale(2, RoundingMode.HALF_UP);

        entity.setFinReal(fin);
        entity.setMinutosConsumidos((int) minutos);
        entity.setTotalCalculado(total);
        entity.setEstado(SesionEntity.EstadoSesion.CERRADA);

        // Liberar estacion via Feign
        estacionClient.cambiarEstado(entity.getEstacionId(), "DISPONIBLE");

        return toResponse(sesionRepository.save(entity));
    }

    public SesionResponseDTO buscarPorId(Long id) {
        return toResponse(sesionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada")));
    }

    public List<SesionResponseDTO> listar() {
        return sesionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SesionResponseDTO> listarPorUsuario(Long usuarioId) {
        return sesionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SesionResponseDTO> listarActivas() {
        return sesionRepository.findByEstado(SesionEntity.EstadoSesion.ACTIVA)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SesionResponseDTO toResponse(SesionEntity entity) {
        SesionResponseDTO dto = new SesionResponseDTO();
        dto.setId(entity.getId());
        dto.setReservaId(entity.getReservaId());
        dto.setEstacionId(entity.getEstacionId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setInicioReal(entity.getInicioReal());
        dto.setFinReal(entity.getFinReal());
        dto.setMinutosConsumidos(entity.getMinutosConsumidos());
        dto.setTarifaPorHora(entity.getTarifaPorHora());
        dto.setTotalCalculado(entity.getTotalCalculado());
        dto.setEstado(entity.getEstado().name());
        return dto;
    }
}

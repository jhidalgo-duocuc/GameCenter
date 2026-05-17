package cl.gamecenter.reserva.service;

import cl.gamecenter.reserva.client.EstacionClient;
import cl.gamecenter.reserva.dto.EstacionClientDTO;
import cl.gamecenter.reserva.dto.ReservaRequestDTO;
import cl.gamecenter.reserva.dto.ReservaResponseDTO;
import cl.gamecenter.reserva.entity.ReservaEntity;
import cl.gamecenter.reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final EstacionClient estacionClient;

    public ReservaResponseDTO crear(ReservaRequestDTO dto) {

        // 1. Verificar que la estacion existe y está disponible via Feign
        EstacionClientDTO estacion = estacionClient.buscarPorId(dto.getEstacionId());

        if (!estacion.getEstado().equals("DISPONIBLE")) {
            throw new RuntimeException("La estacion no está disponible");
        }

        // 2. Verificar que no hay reservas activas en ese rango de fechas
        boolean ocupada = reservaRepository
                .existsByEstacionIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
                        dto.getEstacionId(),
                        List.of(ReservaEntity.EstadoReserva.PENDIENTE, ReservaEntity.EstadoReserva.CONFIRMADA, ReservaEntity.EstadoReserva.EN_CURSO),
                        dto.getFechaFin(),
                        dto.getFechaInicio()
                );

        if (ocupada) {
            throw new RuntimeException("La estacion ya tiene una reserva en ese horario");
        }

        // 3. Crear la reserva
        ReservaEntity entity = new ReservaEntity();
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setEstacionId(dto.getEstacionId());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setNotas(dto.getNotas());

        ReservaEntity guardada = reservaRepository.save(entity);

        return toResponse(guardada);
    }

    public List<ReservaResponseDTO> listar() {
        return reservaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReservaResponseDTO buscarPorId(Long id) {
        return toResponse(reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada")));
    }

    public List<ReservaResponseDTO> listarPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ReservaResponseDTO cancelar(Long id) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (entity.getEstado() == ReservaEntity.EstadoReserva.COMPLETADA ||
                entity.getEstado() == ReservaEntity.EstadoReserva.CANCELADA) {
            throw new RuntimeException("No se puede cancelar una reserva " +
                    entity.getEstado().name().toLowerCase());
        }

        entity.setEstado(ReservaEntity.EstadoReserva.CANCELADA);
        return toResponse(reservaRepository.save(entity));
    }

    public ReservaResponseDTO confirmar(Long id) {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        entity.setEstado(ReservaEntity.EstadoReserva.CONFIRMADA);
        return toResponse(reservaRepository.save(entity));
    }

    private ReservaResponseDTO toResponse(ReservaEntity entity) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setEstacionId(entity.getEstacionId());
        dto.setFechaInicio(entity.getFechaInicio());
        dto.setFechaFin(entity.getFechaFin());
        dto.setEstado(entity.getEstado().name());
        dto.setNotas(entity.getNotas());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}

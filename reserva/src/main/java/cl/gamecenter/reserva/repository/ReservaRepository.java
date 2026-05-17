package cl.gamecenter.reserva.repository;

import cl.gamecenter.reserva.entity.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    List<ReservaEntity> findByUsuarioId(Long usuarioId);

    List<ReservaEntity> findByEstacionId(Long estacionId);

    List<ReservaEntity> findByEstado(ReservaEntity.EstadoReserva estado);

    List<ReservaEntity> findByUsuarioIdAndEstado(Long usuarioId, ReservaEntity.EstadoReserva estado);

    // Verifica si una estacion tiene reservas activas en un rango de fechas
    boolean existsByEstacionIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
            Long estacionId,
            List<ReservaEntity.EstadoReserva> estados,
            LocalDateTime fechaFin,
            LocalDateTime fechaInicio
    );
}

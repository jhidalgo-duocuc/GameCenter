package cl.gamecenter.control_tiempo.repository;

import cl.gamecenter.control_tiempo.entity.SesionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SesionEntity, Long> {

    Optional<SesionEntity> findByReservaIdAndEstado(Long reservaId, SesionEntity.EstadoSesion estado);

    List<SesionEntity> findByUsuarioId(Long usuarioId);

    List<SesionEntity> findByEstacionId(Long estacionId);

    List<SesionEntity> findByEstado(SesionEntity.EstadoSesion estado);

    Optional<SesionEntity> findByEstacionIdAndEstado(Long estacionId, SesionEntity.EstadoSesion estado);
}
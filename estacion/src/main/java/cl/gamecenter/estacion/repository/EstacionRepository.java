package cl.gamecenter.estacion.repository;

import cl.gamecenter.estacion.entity.EstacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstacionRepository extends JpaRepository<EstacionEntity, Long> {

    List<EstacionEntity> findByEstado(EstacionEntity.EstadoEstacion estado);

    List<EstacionEntity> findByTipoEstacionId(Long tipoEstacionId);

    List<EstacionEntity> findByTipoEstacionIdAndEstado(
            Long tipoEstacionId,
            EstacionEntity.EstadoEstacion estado
    );
}

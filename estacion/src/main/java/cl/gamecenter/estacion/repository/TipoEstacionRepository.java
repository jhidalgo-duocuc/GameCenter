package cl.gamecenter.estacion.repository;

import cl.gamecenter.estacion.entity.TipoEstacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoEstacionRepository extends JpaRepository<TipoEstacionEntity, Long> {

    Optional<TipoEstacionEntity> findByNombre(String nombre);

    List<TipoEstacionEntity> findByActivoTrue();
}

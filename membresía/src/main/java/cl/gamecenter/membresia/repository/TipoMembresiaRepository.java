package cl.gamecenter.membresia.repository;

import cl.gamecenter.membresia.entity.TipoMembresiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoMembresiaRepository extends JpaRepository<TipoMembresiaEntity, Long> {

    Optional<TipoMembresiaEntity> findByNombre(String nombre);

    List<TipoMembresiaEntity> findByActivoTrue();
}

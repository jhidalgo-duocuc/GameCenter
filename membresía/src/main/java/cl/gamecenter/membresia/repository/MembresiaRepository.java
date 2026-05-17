package cl.gamecenter.membresia.repository;

import cl.gamecenter.membresia.entity.MembresiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembresiaRepository extends JpaRepository<MembresiaEntity, Long> {

    List<MembresiaEntity> findByUsuarioId(Long usuarioId);

    Optional<MembresiaEntity> findByUsuarioIdAndEstado(Long usuarioId, MembresiaEntity.EstadoMembresia estado);

    boolean existsByUsuarioIdAndEstado(Long usuarioId, MembresiaEntity.EstadoMembresia estado);
}
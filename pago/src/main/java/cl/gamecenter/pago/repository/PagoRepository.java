package cl.gamecenter.pago.repository;

import cl.gamecenter.pago.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
}

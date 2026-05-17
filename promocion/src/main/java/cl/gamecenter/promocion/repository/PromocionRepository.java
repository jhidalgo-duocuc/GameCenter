package cl.gamecenter.promocion.repository;

import cl.gamecenter.promocion.entity.PromocionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionRepository extends JpaRepository<PromocionEntity, Long> {
}

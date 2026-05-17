package cl.gamecenter.notificacion.repository;

import cl.gamecenter.notificacion.entity.NotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Long> {
}

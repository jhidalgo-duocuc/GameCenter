package cl.gamecenter.lista_espera.repository;

import cl.gamecenter.lista_espera.entity.ConfigEsperaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigEsperaRepository extends JpaRepository<ConfigEsperaEntity, Long> {
}

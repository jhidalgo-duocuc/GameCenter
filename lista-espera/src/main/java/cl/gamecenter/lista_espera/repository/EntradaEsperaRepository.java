package cl.gamecenter.lista_espera.repository;

import cl.gamecenter.lista_espera.entity.EntradaEsperaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntradaEsperaRepository extends JpaRepository<EntradaEsperaEntity, Long> {
}

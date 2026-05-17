package cl.gamecenter.usuario.repository;

import cl.gamecenter.usuario.entity.TokenAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenAuthRepository extends JpaRepository<TokenAuthEntity, Long> {

    Optional<TokenAuthEntity> findByToken(String token);

    boolean existsByToken(String token);
}

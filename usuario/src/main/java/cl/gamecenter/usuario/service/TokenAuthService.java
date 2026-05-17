package cl.gamecenter.usuario.service;

import cl.gamecenter.usuario.dto.LoginRequestDTO;
import cl.gamecenter.usuario.dto.LoginResponseDTO;
import cl.gamecenter.usuario.entity.TokenAuthEntity;
import cl.gamecenter.usuario.entity.UsuarioEntity;
import cl.gamecenter.usuario.repository.TokenAuthRepository;
import cl.gamecenter.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenAuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenAuthRepository tokenAuthRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO dto) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        // Token simple por ahora (luego se reemplaza por JWT)
        String tokenValue = UUID.randomUUID().toString();

        TokenAuthEntity token = new TokenAuthEntity();
        token.setUsuario(usuario);
        token.setToken(tokenValue);
        token.setTipo(TokenAuthEntity.TipoToken.ACCESS);
        token.setExpiracion(LocalDateTime.now().plusHours(8));
        token.setUsado(false);

        tokenAuthRepository.save(token);

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken(tokenValue);
        response.setTipo("Bearer");
        response.setExpiraEn(token.getExpiracion());
        response.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido());
        response.setRolNombre(usuario.getRol().getNombre());

        return response;
    }

    public void invalidarToken(String token) {
        TokenAuthEntity entity = tokenAuthRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no encontrado"));
        entity.setUsado(true);
        tokenAuthRepository.save(entity);
    }
}

package cl.gamecenter.usuario.service;

import cl.gamecenter.usuario.dto.UsuarioRequestDTO;
import cl.gamecenter.usuario.dto.UsuarioResponseDTO;
import cl.gamecenter.usuario.entity.RolEntity;
import cl.gamecenter.usuario.entity.UsuarioEntity;
import cl.gamecenter.usuario.repository.RolRepository;
import cl.gamecenter.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        RolEntity rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setTelefono(dto.getTelefono());
        entity.setRol(rol);

        UsuarioEntity guardado = usuarioRepository.save(entity);
        return toResponse(guardado);
    }

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toResponse(entity);
    }

    public UsuarioResponseDTO buscarPorEmail(String email) {
        UsuarioEntity entity = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toResponse(entity);
    }

    public void desactivar(Long id) {
        UsuarioEntity entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        entity.setActivo(false);
        usuarioRepository.save(entity);
    }

    // Mapper manual entity → DTO
    private UsuarioResponseDTO toResponse(UsuarioEntity entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setActivo(entity.getActivo());
        dto.setRolNombre(entity.getRol().getNombre());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}

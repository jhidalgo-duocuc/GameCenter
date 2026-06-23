package cl.gamecenter.usuario.service;

import cl.gamecenter.usuario.dto.UsuarioRequestDTO;
import cl.gamecenter.usuario.dto.UsuarioResponseDTO;
import cl.gamecenter.usuario.entity.RolEntity;
import cl.gamecenter.usuario.entity.UsuarioEntity;
import cl.gamecenter.usuario.repository.RolRepository;
import cl.gamecenter.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioRequestDTO requestDTO;
    private RolEntity rolEntity;
    private UsuarioEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        requestDTO = new UsuarioRequestDTO();
        requestDTO.setNombre("Juan");
        requestDTO.setApellido("Pérez");
        requestDTO.setEmail("juan@example.com");
        requestDTO.setPassword("password123");
        requestDTO.setTelefono("+56912345678");
        requestDTO.setRolId(2L);

        rolEntity = new RolEntity();
        rolEntity.setId(2L);
        rolEntity.setNombre("CLIENTE");
        rolEntity.setDescripcion("Cliente del game center");

        entidadGuardada = new UsuarioEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Juan");
        entidadGuardada.setApellido("Pérez");
        entidadGuardada.setEmail("juan@example.com");
        entidadGuardada.setTelefono("+56912345678");
        entidadGuardada.setActivo(true);
        entidadGuardada.setRol(rolEntity);
        entidadGuardada.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("registrar() - debe crear usuario cuando email no existe y rol es válido")
    void registrar_debeCrearUsuario_cuandoEmailNoExisteYRolValido() {
        when(usuarioRepository.existsByEmail("juan@example.com")).thenReturn(false);
        when(rolRepository.findById(2L)).thenReturn(Optional.of(rolEntity));
        when(passwordEncoder.encode("password123")).thenReturn("hash123");
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(entidadGuardada);

        UsuarioResponseDTO resultado = usuarioService.registrar(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEmail()).isEqualTo("juan@example.com");
        assertThat(resultado.getRolNombre()).isEqualTo("CLIENTE");
        verify(usuarioRepository).save(any(UsuarioEntity.class));
    }

    @Test
    @DisplayName("registrar() - debe lanzar excepción cuando email ya está registrado")
    void registrar_debeLanzarExcepcion_cuandoEmailYaRegistrado() {
        when(usuarioRepository.existsByEmail("juan@example.com")).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.registrar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("email ya está registrado");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("registrar() - debe lanzar excepción cuando rol no existe")
    void registrar_debeLanzarExcepcion_cuandoRolNoExiste() {
        when(usuarioRepository.existsByEmail("juan@example.com")).thenReturn(false);
        when(rolRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.registrar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Rol no encontrado");

        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("buscarPorId() - debe retornar usuario cuando existe")
    void buscarPorId_debeRetornarUsuario_cuandoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        UsuarioResponseDTO resultado = usuarioService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("buscarPorId() - debe lanzar excepción cuando no existe")
    void buscarPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    @DisplayName("buscarPorEmail() - debe retornar usuario cuando existe")
    void buscarPorEmail_debeRetornarUsuario_cuandoExiste() {
        when(usuarioRepository.findByEmail("juan@example.com")).thenReturn(Optional.of(entidadGuardada));

        UsuarioResponseDTO resultado = usuarioService.buscarPorEmail("juan@example.com");

        assertThat(resultado.getEmail()).isEqualTo("juan@example.com");
    }

    @Test
    @DisplayName("listar() - debe retornar todos los usuarios")
    void listar_debeRetornarTodosLosUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<UsuarioResponseDTO> resultado = usuarioService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("desactivar() - debe marcar usuario como inactivo")
    void desactivar_debeMarcarUsuarioComoInactivo() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(entidadGuardada);

        usuarioService.desactivar(1L);

        verify(usuarioRepository).save(argThat(usuario -> !usuario.getActivo()));
    }

    @Test
    @DisplayName("desactivar() - debe lanzar excepción cuando usuario no existe")
    void desactivar_debeLanzarExcepcion_cuandoUsuarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.desactivar(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Usuario no encontrado");
    }
}

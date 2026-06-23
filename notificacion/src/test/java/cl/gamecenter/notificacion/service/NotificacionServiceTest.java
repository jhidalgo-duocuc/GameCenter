package cl.gamecenter.notificacion.service;

import cl.gamecenter.notificacion.client.UsuarioClient;
import cl.gamecenter.notificacion.dto.NotificacionRequestDTO;
import cl.gamecenter.notificacion.dto.NotificacionResponseDTO;
import cl.gamecenter.notificacion.dto.UsuarioClientDTO;
import cl.gamecenter.notificacion.entity.NotificacionEntity;
import cl.gamecenter.notificacion.entity.NotificacionEntity.CanalNotificacion;
import cl.gamecenter.notificacion.entity.NotificacionEntity.TipoNotificacion;
import cl.gamecenter.notificacion.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private NotificacionService notificacionService;

    private NotificacionRequestDTO requestDTO;
    private NotificacionEntity entidadGuardada;
    private UsuarioClientDTO usuarioActivo;

    @BeforeEach
    void setUp() {
        requestDTO = new NotificacionRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setTipo("SISTEMA");
        requestDTO.setTitulo("Bienvenido");
        requestDTO.setMensaje("Tu cuenta fue creada exitosamente");
        requestDTO.setLeida(false);
        requestDTO.setCanal("APP");
        requestDTO.setCreatedAt(LocalDateTime.now());

        usuarioActivo = new UsuarioClientDTO();
        usuarioActivo.setId(1L);
        usuarioActivo.setActivo(true);

        entidadGuardada = new NotificacionEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setUsuarioId(1L);
        entidadGuardada.setTipo(TipoNotificacion.SISTEMA);
        entidadGuardada.setTitulo("Bienvenido");
        entidadGuardada.setMensaje("Tu cuenta fue creada exitosamente");
        entidadGuardada.setLeida(false);
        entidadGuardada.setCanal(CanalNotificacion.APP);
        entidadGuardada.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("crear() - debe crear notificación cuando usuario está activo")
    void crear_debeCrearNotificacion_cuandoUsuarioActivo() {
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(notificacionRepository.save(any(NotificacionEntity.class))).thenReturn(entidadGuardada);

        NotificacionResponseDTO resultado = notificacionService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getUsuarioId()).isEqualTo(1L);
        assertThat(resultado.getTipo()).isEqualTo("SISTEMA");
        verify(notificacionRepository).save(any(NotificacionEntity.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando usuario no está activo")
    void crear_debeLanzarExcepcion_cuandoUsuarioNoActivo() {
        usuarioActivo.setActivo(false);
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);

        assertThatThrownBy(() -> notificacionService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activo");

        verify(notificacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("obtenerPorId() - debe retornar notificación cuando existe")
    void obtenerPorId_debeRetornarNotificacion_cuandoExiste() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        NotificacionResponseDTO resultado = notificacionService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getTitulo()).isEqualTo("Bienvenido");
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notificacionService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Notificacion no encontrada");
    }

    @Test
    @DisplayName("listar() - debe retornar todas las notificaciones")
    void listar_debeRetornarTodasLasNotificaciones() {
        when(notificacionRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<NotificacionResponseDTO> resultado = notificacionService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("eliminar() - debe eliminar notificación cuando existe")
    void eliminar_debeEliminarNotificacion_cuandoExiste() {
        when(notificacionRepository.existsById(1L)).thenReturn(true);

        notificacionService.eliminar(1L);

        verify(notificacionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando notificación no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        when(notificacionRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> notificacionService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Notificacion no encontrada");

        verify(notificacionRepository, never()).deleteById(any());
    }
}
